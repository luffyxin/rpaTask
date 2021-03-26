package com.example.demo.dao;

import com.example.demo.entity.ParamData;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface ParamDataMapper extends Mapper<ParamData> {

    public ParamData selectByKey(String key);

}