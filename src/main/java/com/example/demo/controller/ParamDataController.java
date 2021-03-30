package com.example.demo.controller;


import com.example.demo.base.response.ApiParam;
import com.example.demo.base.response.ApiResult;
import com.example.demo.base.response.RpaResponse;
import com.example.demo.biz.ParamDataBiz;
import com.example.demo.entity.ParamData;
import com.example.demo.util.JsonUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/param-data")
public class ParamDataController {

    @Resource
    private ParamDataBiz paramDataBiz;


    @PostMapping("/create")
    public String createParamData(@RequestBody ParamData paramData){
        paramDataBiz.createParamData(paramData);
        return JsonUtil.toJSONL2String(RpaResponse.getSuccess());
    }

    @PostMapping("/get")
    public String getParamData(@RequestBody ParamData data){
        ParamData paramData = paramDataBiz.getParamData(data.getKey());
        return JsonUtil.toJSONL2String(RpaResponse.getSuccessData(paramData.getValue()));
    }

}
