package com.example.demo.dao;

import com.example.demo.entity.Process;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface ProcessMapper extends Mapper<Process> {

    List<Process> getNameList();

    List<Process> getProcessList();

    int logicDelete(Long id);

    int updateProcess(Process process);

}