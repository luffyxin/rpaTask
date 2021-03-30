package com.example.demo.biz;


import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.base.Exception.BizException;
import com.example.demo.base.Exception.RpaException;
import com.example.demo.base.response.ApiResult;
import com.example.demo.base.response.RpaResponse;
import com.example.demo.dao.ParamDataMapper;
import com.example.demo.entity.ParamData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class ParamDataBiz {

    private static final Snowflake snowflake = IdUtil.getSnowflake(1, 1);

    @Resource
    private ParamDataMapper paramDataMapper;

    public void createParamData(ParamData paramData) {
        Long id = snowflake.nextId();
        paramData.setCreateTime(new Date());
        paramData.setUpdateTime(new Date());
        paramData.setId(id);
        // 如果传过来的是json对象的话需要转换成字符串
        if (paramData.getJsonValue() != null) {
            paramData.setValue(paramData.getJsonValue().toJSONString());
        }
        ParamData data = getParamData(paramData.getKey());
        // 不能有同名key
        if (data == null) {
            paramDataMapper.insertSelective(paramData);
        } else {
            throw new RpaException(RpaResponse.getErrorResult("30068"));
        }

    }

    public ParamData getParamData(String key) {
        return paramDataMapper.selectByKey(key);
    }

}
