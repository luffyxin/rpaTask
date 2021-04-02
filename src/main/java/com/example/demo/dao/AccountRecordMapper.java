package com.example.demo.dao;

import com.example.demo.entity.AccountRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface AccountRecordMapper extends Mapper<AccountRecord> {


    public void updateStatusById(AccountRecord accountRecord);

    public void selectByStatus(String status);

    public List<AccountRecord> selectRobotUsed(@Param("robotId") Long robotId, @Param("status") String status);
}