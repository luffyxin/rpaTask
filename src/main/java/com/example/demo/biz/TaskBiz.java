package com.example.demo.biz;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.base.Exception.BizException;
import com.example.demo.base.response.ApiExtend;
import com.example.demo.base.response.ApiParam;
import com.example.demo.base.response.ApiResult;
import com.example.demo.config.MinioConfig;
import com.example.demo.constant.CommonConstant;
import com.example.demo.constant.EnumTaskStatus;
import com.example.demo.dao.TaskMapper;
import com.example.demo.entity.DataFile;
import com.example.demo.entity.Task;
import com.example.demo.entity.TaskDataSearchDto;
import com.example.demo.entity.TaskVo;
import com.example.demo.util.DataUtil;
import com.example.demo.util.JsonUtil;
import com.example.demo.util.StringUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mongodb.client.MongoCollection;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.bson.Document;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TaskBiz {

    @Resource
    private TaskMapper taskMapper;

    @Resource
    private MinioConfig minioConfig;

    @Resource
    private MongoTemplate mongoTemplate;

    @Resource
    private DataUtil dataUtil;

    @Resource
    private MinioClient minioClient;

    private static final Snowflake snowflake = IdUtil.getSnowflake(1, 1);

    /**
     * ????????????
     *
     * @param task ??????
     */
    public Long createTask(Task task) {
        Long id = snowflake.nextId();
        task.setStatus(EnumTaskStatus.CREATE_FINISH.getMsg());
        task.setId(id);
        task.setCreateTime(new Date());
        task.setUpdateTime(new Date());
        taskMapper.insertSelective(task);
        return id;
    }

    /**
     * ????????????????????????????????????
     */
    @Scheduled(cron = "0 1/2 * * * ? ")
    public void timeOutTaskScan() {
        int i = taskMapper.updateTimeOutTask();
        if (i > 0) {
            log.info("?????????{}???????????????????????????", i);
        } else {
            log.info("????????????????????????");
        }
    }

    /**
     * ??????????????????????????????????????????????????????
     */
    @Scheduled(cron = "0 1/1 * * * ? ")
    public void timeToSelectTask() {
        log.info("start scan task to run");
        // ??????????????????????????????????????????
        Example example = new Example(Task.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("status", EnumTaskStatus.UPLOAD_DATA.getMsg());
        criteria.orEqualTo("status", EnumTaskStatus.START.getMsg());
        criteria.orEqualTo("status", EnumTaskStatus.RUNNING.getMsg());
        criteria.andEqualTo("deleteFlg", 0);
        List<Task> taskList = taskMapper.selectByExample(example);
        // ?????????id??????task
        Map<Long, List<Task>> taskMap = taskList.stream().collect(Collectors.groupingBy(Task::getGroupId));

        taskMap.forEach((groupId, tList) -> {
            // ???????????????????????????????????????????????????
            boolean existRunTask = tList.stream().anyMatch(t -> t.getStatus().equals(EnumTaskStatus.RUNNING.getMsg()));
            boolean existStartTask = tList.stream().anyMatch(t -> t.getStatus().equals(EnumTaskStatus.START.getMsg()));
            if (!(existRunTask || existStartTask)) {
                // ?????????????????????????????????????????????????????????
                Optional<Task> firstTask = tList.stream().min((t1, t2) -> {
                    if (t2.getPriority().equals(t1.getPriority())) {
                        return t1.getCreateTime().compareTo(t2.getCreateTime());
                    } else {
                        return -t1.getPriority().compareTo(t2.getPriority());
                    }
                });
                // ?????????????????????????????????????????????????????????
                firstTask.ifPresent(ft -> {
                    ft.setUpdateTime(new Date());
                    ft.setStartTime(new Date());
                    ft.setStatus(EnumTaskStatus.START.getMsg());
                    log.info("start task {} taskId-{} in groupId-{}", ft.getDataName(), ft.getId(), ft.getGroupId());
                    taskMapper.updateByPrimaryKeySelective(ft);
                    // todo ??????sass ????????????????????????????????????

                });
            }
        });
    }

    public void exportResultData(Long taskId) {
        Task task = taskMapper.selectById(taskId);
        String collectionName = task.getDataName() + CommonConstant.DATA_SUFFIX;
        exportMongodbData(task, collectionName, CommonConstant.TASK_URL_RESULT);
    }

    public void exportResourceData(Long taskId) {
        Task task = taskMapper.selectById(taskId);
        String collectionName = task.getDataName();
        exportMongodbData(task, collectionName, CommonConstant.TASK_URL_RESOURCE);
    }

    public ApiParam<TaskVo> getTaskList(Integer page, Integer limit, Task task) {
        Page<Object> result = PageHelper.startPage(page, limit);
        taskMapper.selectTaskList(task);
        ApiExtend apiExtend = new ApiExtend();
        apiExtend.setTotal(result.getTotal());
        return new ApiParam<>(result.getResult(), apiExtend, ApiResult.getOkResult());
    }

    public void deleteTask(Long taskId) {
        Task task = taskMapper.selectById(taskId);
        // ????????????????????????????????????????????????
        if (EnumTaskStatus.START.getMsg().equals(task.getStatus())
                || EnumTaskStatus.RUNNING.getMsg().equals(task.getStatus())) {
            throw new BizException(ApiResult.getErrorResult("30061"));
        } else {
            taskMapper.deleteTask(taskId);
            // ???????????????
            if (StringUtils.isNotEmpty(task.getDataName())) {
                mongoTemplate.dropCollection(task.getDataName());
            }
        }
    }

    public void updateTask(Task task) {
        task.setUpdateTime(new Date());
        taskMapper.updateTask(task);
    }


    /**
     * ????????????
     *
     * @param task ??????
     */
    public void exportMongodbData(Task task, String collectionName, String type) {
        final String[] url = {""};
        ByteArrayOutputStream excelOutput = new ByteArrayOutputStream();
        final InputStream[] newInput = {null};
        // ??????????????????
        ThreadUtil.execute(() -> {
            try {
                // ???mongodb ??????????????????
                List<JSONObject> all = mongoTemplate.findAll(JSONObject.class, collectionName);
                ExcelWriter writer = ExcelUtil.getWriter(true);
                writer.write(all);
                writer.flush(excelOutput);
                writer.close();
                newInput[0] = new ByteArrayInputStream(excelOutput.toByteArray());
                // ?????????minio
                minioClient.putObject(
                        PutObjectArgs.builder().bucket(CommonConstant.BUCKET).object(collectionName + CommonConstant.XLSX)
                                .stream(
                                        newInput[0], newInput[0].available(), -1)
                                .build());
                // ????????????url
                url[0] = minioConfig.getEndpoint() + "/" + CommonConstant.BUCKET + "/" + collectionName + CommonConstant.XLSX;

                if (CommonConstant.TASK_URL_RESOURCE.equals(type)) {
                    task.setResourceUrl(url[0]);
                } else if (CommonConstant.TASK_URL_RESULT.equals(type)) {
                    task.setResultUrl(url[0]);
                }
                taskMapper.updateByPrimaryKeySelective(task);
            } catch (Exception e) {
                log.error(e.getMessage());
            } finally {
                try {
                    newInput[0].close();
                    excelOutput.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Task getTask(Long taskId) {
        return taskMapper.selectById(taskId);
    }

    public List<JSONObject> checkTaskData(TaskDataSearchDto taskDataSearchDto) {
        Long taskId = taskDataSearchDto.getTaskId();

        Task task = taskMapper.selectById(taskId);

        String dataName = task.getDataName();
        Query query = new Query(Criteria.where(taskDataSearchDto.getTitle()).is(taskDataSearchDto.getValue()));

        List<JSONObject> objects = mongoTemplate.find(query, JSONObject.class, dataName);

        return objects;
    }

    // ??????json??????
    public Task uploadJsonData(DataFile dataFile) {
        Task task = taskMapper.selectById(dataFile.getTaskId());
        if (task == null) {
            throw new BizException(ApiResult.getErrorResult("30066"));
        } else {
            String collectionName = task.getName()
                    + DateFormatUtils.format(new Date(), "yyyyMMdd");
            // ???????????????
            MongoCollection<Document> doc = dataUtil.getCollectionByName(collectionName);
            List<Document> mapList = new ArrayList<>();
            List<JSONObject> jsonData = dataFile.getJsonData();
            jsonData.forEach(j -> mapList.add(Document.parse(j.toJSONString())));
            saveDataToMongo(task, collectionName, doc, (long) mapList.size(), mapList);
        }
        return task;
    }

    /**
     * ???oss???????????????????????????????????????mongodb
     *
     * @param dataFile ??????????????????
     */
    public Task uploadDataFile(DataFile dataFile) {
        // ??????????????????????????????
        Task task = taskMapper.selectById(dataFile.getTaskId());
        log.info("task-data start upload");
        ThreadUtil.execute(() -> {
            InputStream stream = null;
            // ???minio??????????????????
            try {
                stream = minioClient.getObject(
                        GetObjectArgs.builder().bucket(dataFile.getBucketName()).object(dataFile.getObjectName()).build());
                log.info("task-data download from minio finish");
                // ??????excel ??????
                ExcelReader reader = ExcelUtil.getReader(stream);

                String collectionName = task.getName()
                        + DateFormatUtils.format(new Date(), "yyyyMMdd");
                // ???????????????
                MongoCollection<Document> doc = dataUtil.getCollectionByName(collectionName);
                List<Map<String, Object>> readAll = reader.readAll();
                List<Document> mapList = new ArrayList<>();
                readAll.forEach(m -> {
                    cn.hutool.json.JSONObject json = JSONUtil.parseObj(m,false, true);
                    json.setDateFormat(DatePattern.ISO8601_PATTERN);
                    mapList.add(Document.parse(json.toString()));
                });

                saveDataToMongo(task, collectionName, doc, (long) readAll.size(), mapList);

            } catch (Exception e) {
                log.warn("Error occurred: " + e);
                // ????????????????????????
                task.setStatus(EnumTaskStatus.UPLOAD_DATA_ERROR.getMsg());
                task.setUpdateTime(new Date());
                // ????????????
                taskMapper.updateByPrimaryKeySelective(task);
            }
        });

        return task;
    }

    private void saveDataToMongo(Task task, String collectionName, MongoCollection<Document> doc, Long size, List<Document> mapList) {
        doc.insertMany(mapList);
        Query query = new Query(Criteria.where(CommonConstant.DATA_STATUS_FLG).is(null));
        log.info("task-data download insert to mongodb finish");
        // ???????????????????????????????????????
        mongoTemplate.
                updateMulti(query, Update.
                                update(CommonConstant.DATA_STATUS_FLG, CommonConstant.DATA_STATUS_WAIT),
                        JSONObject.class, collectionName);
        // ??????????????????
        task.setDataSum(size + task.getDataSum());
        task.setDataName(collectionName);
        task.setStatus(EnumTaskStatus.UPLOAD_DATA.getMsg());
        task.setUpdateTime(new Date());
        // ????????????
        taskMapper.updateByPrimaryKeySelective(task);
        log.info("task-data upload finish");
    }


}
