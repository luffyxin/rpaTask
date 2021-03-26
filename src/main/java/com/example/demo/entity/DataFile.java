package com.example.demo.entity;

import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DataFile {

    private String bucketName;

    private String objectName;

    private Long taskId;

    private List<JSONObject> jsonData;

}
