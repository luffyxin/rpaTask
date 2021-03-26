package com.example.demo.util;

import com.alibaba.fastjson.JSONObject;
import com.mongodb.client.MongoCollection;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


@Slf4j
@Component
public class DataUtil {

    @Resource
    private MongoTemplate mongoTemplate;

    public MongoCollection<Document> getCollectionByName(String collectionName) {
        MongoCollection<Document> taskCollection;
        if (mongoTemplate.collectionExists(collectionName)) {
            //存在
            taskCollection = mongoTemplate.getCollection(collectionName);
        } else {
            //不存在
            taskCollection = mongoTemplate.createCollection(collectionName);
        }
        return taskCollection;
    }


    /**
     * objectId 转 _id
     *
     * @param oId objectId
     * @return _id
     */
    public ObjectId objectIdToId(String oId) {
        JSONObject jsonObject = JSONObject.parseObject(oId);
        return new ObjectId(jsonObject.getInteger("timestamp"),
                jsonObject.getInteger("machineIdentifier"),
                jsonObject.getShort("processIdentifier"),
                jsonObject.getInteger("counter"));
    }

}
