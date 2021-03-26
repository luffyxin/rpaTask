package com.example.demo;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.constant.CommonConstant;
import com.example.demo.dao.GroupMapper;
import com.example.demo.dao.RobotMapper;
import com.example.demo.dao.TaskMapper;
import com.example.demo.entity.Account;
import com.example.demo.entity.GroupVo;
import com.example.demo.entity.Robot;
import com.example.demo.entity.Task;
import com.example.demo.util.DataUtil;
import com.example.demo.util.JsonUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mongodb.client.MongoCollection;
import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.redisson.Redisson;
import org.redisson.api.RList;
import org.redisson.api.RQueue;
import org.redisson.api.RedissonClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@SpringBootTest
class DemoApplicationTests {

    @Resource
    private DataUtil dataUtil;


    @Resource
    private MongoTemplate mongoTemplate;

    @Resource
    private RobotMapper robotMapper;

    @Resource
    private GroupMapper groupMapper;

    @Resource
    private TaskMapper taskMapper;

    @Resource
    private RedissonClient redissonClient;

    @Test
    void contextLoads() {
        RQueue<Object> anyQueue = redissonClient.getQueue("anyQueue");
        Object peek = anyQueue.peek();
        Account account = JsonUtil.parseObject((String) peek, Account.class);
        System.out.println(account.getAccount());
    }


    @Test
    public void writeToMongo(List<Document> mapList, String collectionName) {
        MongoCollection<Document> doc = dataUtil.getCollectionByName(collectionName);
        doc.insertMany(mapList);
    }


    @Test
    public void hutuExeclRead() {
        InputStream stream = null;
        try {
            MinioClient minioClient =
                    MinioClient.builder()
                            .endpoint("http://127.0.0.1:9000")
                            .credentials("admin", "password")
                            .build();
            stream = minioClient.getObject(
                    GetObjectArgs.builder().bucket("task-data").object("待处理数据表.xlsx").build());

        } catch (MinioException | InvalidKeyException | IOException | NoSuchAlgorithmException e) {
            System.out.println("Error occurred: " + e);
        }


        ExcelReader reader = ExcelUtil.getReader(stream);

        MongoCollection<Document> doc = dataUtil.getCollectionByName("data2");

        List<Map<String, Object>> readAll = reader.readAll();

        List<Document> mapList = new ArrayList<>();

        readAll.forEach(m -> {
            mapList.add(Document.parse(JSONObject.toJSONString(m)));
        });

        doc.insertMany(mapList);
        log.info(readAll.toString());
    }

    @Test
    public void mongoTest() {
        String collectionName = "根据公众号名称获取其最新文章信息_任务120210304";
        JSONObject one = mongoTemplate.findOne(new Query(), JSONObject.class, collectionName);
        ObjectId objectId = dataUtil.objectIdToId(one.getJSONObject("_id").toJSONString());
        Query query = new Query(Criteria.where("source_id").is(objectId.toString()));
        collectionName = "根据公众号名称获取其最新文章信息_任务120210304_result";
        JSONObject one1 = mongoTemplate.findOne(query, JSONObject.class, collectionName);
        System.out.println(one1);
    }


    @Test
    public void testRobotGroup(){
        List<GroupVo> groupList = groupMapper.getGroupList(1,1);
        System.out.println(groupList.toString());
    }



    @Test
    public void updateTaskData() {
        String collectionName = "根据公众号名称获取其最新文章信息_任务120210304";
        mongoTemplate.updateMulti(new Query(),
                Update.update(CommonConstant.DATA_STATUS_FLG, CommonConstant.DATA_STATUS_WAIT)
                , JSONObject.class, collectionName);
    }

    @Test
    public void mongodbToExcel() throws IOException, ServerException, InsufficientDataException, InternalException, InvalidResponseException, InvalidKeyException, NoSuchAlgorithmException, XmlParserException, ErrorResponseException {
        String collectionName = "根据公众号名称获取其最新文章信息_任务120210304";
        List<JSONObject> all = mongoTemplate.findAll(JSONObject.class, collectionName);
        ByteArrayOutputStream excelOutput = new ByteArrayOutputStream();
        ExcelWriter writer = ExcelUtil.getWriter(true);
        writer.write(all);
        writer.flush(excelOutput);
        writer.close();

        InputStream newInput = new ByteArrayInputStream(excelOutput.toByteArray());

        System.out.println(all.toString());

        MinioClient minioClient =
                MinioClient.builder()
                        .endpoint("http://127.0.0.1:9000")
                        .credentials("admin", "password")
                        .build();

        minioClient.putObject(
                PutObjectArgs.builder().bucket("task-data").object(collectionName + ".xlsx").stream(
                        newInput, newInput.available(), -1)
                        .build());

        String url = minioClient.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                        .method(Method.GET)
                        .bucket("task-data")
                        .object(collectionName + ".xlsx")
                        .expiry(60 * 60 * 24)
                        .build());
        System.out.println(url);

        newInput.close();
        excelOutput.close();
    }


    @Test
    public void datetest() {
        Robot robot = robotMapper.selectByPrimaryKey(1366685284850339840l);
        robot.setCurTaskId(null);
        robotMapper.updateByPrimaryKey(robot);

    }

}
