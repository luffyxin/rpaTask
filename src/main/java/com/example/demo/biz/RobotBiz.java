package com.example.demo.biz;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.base.Exception.BizException;
import com.example.demo.base.response.ApiExtend;
import com.example.demo.base.response.ApiParam;
import com.example.demo.base.response.ApiResult;
import com.example.demo.constant.CommonConstant;
import com.example.demo.constant.EnumTaskStatus;
import com.example.demo.dao.RobotMapper;
import com.example.demo.dao.TaskMapper;
import com.example.demo.entity.*;
import com.example.demo.util.DataUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class RobotBiz {

    @Resource
    private RobotMapper robotMapper;

    @Resource
    private TaskMapper taskMapper;

    @Resource
    private MongoTemplate mongoTemplate;

    @Resource
    private DataUtil dataUtil;

    private static final Snowflake snowflake = IdUtil.getSnowflake(1, 1);

    public void createRobot(Robot robot) {
        Long id = snowflake.nextId();
        robot.setId(id);
        robot.setCreateTime(new Date());
        robot.setUpdateTime(new Date());
        robot.setStatus("创建完成");
        robotMapper.insertSelective(robot);
    }

    public void updateRobot(Robot robot){
        robot.setUpdateTime(new Date());
        robotMapper.updateRobot(robot);
    }

    public void deleteRobot(Long id) {
        Robot robot = robotMapper.selectById(id);
        if (robot.getCurTaskId() == null) {
            robotMapper.logicDelete(id);
        } else {
            throw new BizException(ApiResult.getErrorResult("30065"));
        }
    }

    public ApiParam<RobotVo> getRobotList(Integer page, Integer limit, Robot robot) {
        Page<Object> result = PageHelper.startPage(page, limit);
        List<RobotVo> robotVos = robotMapper.selectRobotList(robot);

        ApiExtend apiExtend = new ApiExtend();
        apiExtend.setTotal(result.getTotal());
        ApiParam res = new ApiParam<RobotVo>(robotVos, apiExtend, ApiResult.getOkResult());
        return res;
    }


    public void finishOneData(JSONObject jsonObject) {
        Long taskId = jsonObject.getLong("taskId");
        ObjectId dataId = dataUtil.objectIdToId(jsonObject.getJSONObject("_id").toString());
        Task task = taskMapper.selectById(taskId);
        // 更新数据状态
        updateDataStatus(task, dataId.toString());
        jsonObject.put("source_id", dataId.toString());
        jsonObject.remove("_id");
        jsonObject.remove(CommonConstant.DATA_STATUS_FLG);
        // 结果数据存入mongodb
        String collectionName = task.getDataName();
        MongoCollection<Document> doc = dataUtil.getCollectionByName(collectionName + CommonConstant.DATA_SUFFIX);
        doc.insertOne(Document.parse(jsonObject.toString()));
    }


    public void updateDataStatus(Task task, String dataId) {
        // 更新数据状态
        Query query = new Query(Criteria.where("_id").is(dataId));
        mongoTemplate.updateFirst(query, Update.
                update(CommonConstant.DATA_STATUS_FLG,
                        CommonConstant.DATA_STATUS_FINISH), JSONObject.class, task.getDataName());
        query = new Query(Criteria.where(CommonConstant.DATA_STATUS_FLG).is(CommonConstant.DATA_STATUS_FINISH));
        // 查询已经处理完了多少条数据
        long finishCount = mongoTemplate.count(query, task.getDataName());
        task.setFinishNum(finishCount);
        // 当已经执行数据等于总数据量的时候 修改任务状态
        if (task.getDataSum().equals(finishCount)) {
            task.setStatus(EnumTaskStatus.FINISH.getMsg());
        }
        task.setUpdateTime(new Date());
        taskMapper.updateByPrimaryKeySelective(task);
    }

    @Transactional
    public String getTaskData(@RequestParam RobotDto robotDto) {

        // 没有可以处理的数据了,更新机器人的状态
        Robot robot = robotMapper.selectById(robotDto.getRobotId());
        String dataResult = "";
        // 根据任务名获取数据集合名
        Long taskId = robotDto.getTaskId();
        Task task = taskMapper.selectByStatusAndId(EnumTaskStatus.RUNNING.getMsg(), taskId);
        // 当前任务没有在执行
        if (task == null) {
            robot.setCurTaskId(null);
            robot.setStatus(CommonConstant.ROBOT_STATUS_FREE);
            robot.setUpdateTime(new Date());
            robot.setDeleteFlg(0);
            robotMapper.updateByPrimaryKey(robot);
            return dataResult;
        }
        String collectionName = task.getDataName();
        // 获取一条等待处理的数据
        Query query = new Query(Criteria.where(CommonConstant.DATA_STATUS_FLG).is(CommonConstant.DATA_STATUS_WAIT));
        // mongodb的findAndModify：查找并修改 是原子操作，可以避免在并发情况下不同机器人获取到同一条数据
        JSONObject data = mongoTemplate.findAndModify(query,
                Update.update(CommonConstant.DATA_STATUS_FLG, CommonConstant.DATA_STATUS_RUNNING)
                , JSONObject.class, collectionName);
        if (data != null) {
            dataResult = data.toString();
        } else {

            robot.setCurTaskId(null);
            robot.setStatus(CommonConstant.ROBOT_STATUS_FREE);
            robot.setUpdateTime(new Date());
            robot.setDeleteFlg(0);
            robotMapper.updateByPrimaryKey(robot);
            // 更新任务状态为 数据分发完毕
            task.setStatus(EnumTaskStatus.DATA_OUT.getMsg());
            task.setUpdateTime(new Date());
            taskMapper.updateByPrimaryKeySelective(task);
        }
        return dataResult;
    }

    @Transactional
    public RobotDto robotStartTask(RobotDto robotDto) {
        // 查询组内开始的或者执行中的任务
        Task searchTask =new Task();
        searchTask.setGroupId(robotDto.getGroupId());
        TaskVo task = taskMapper.getRunTask(searchTask);
        Robot robot = robotMapper.selectById(robotDto.getRobotId());

        if (ObjectUtil.isNotEmpty(task)) {
            // 更新机器人状态
            robot.setStatus(CommonConstant.ROBOT_STATUS_BUSY);
            robot.setCurTaskId(task.getId());
            robot.setUpdateTime(new Date());
            robotDto.setTaskId(task.getId());
            robotDto.setProcessName(task.getProcessName());
            robotMapper.updateByPrimaryKeySelective(robot);

            // 如果任务是开始执行的状态更新任务状态为执行中
            if (EnumTaskStatus.START.getMsg().equals(task.getStatus())) {
                task.setUpdateTime(new Date());
                task.setStatus(EnumTaskStatus.RUNNING.getMsg());
                taskMapper.updateByPrimaryKeySelective(task);
            }
        }
        return robotDto;
    }

}
