package com.example.demo.dao;

import com.example.demo.entity.Task;
import com.example.demo.entity.TaskVo;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface TaskMapper extends Mapper<Task> {


    public Task selectById(Long id);

    public TaskVo selectByStatusAndId(String status,Long id);

    public List<TaskVo> selectTaskList(Task task);

    public int deleteTask(Long taskId);

    public int updateTask(Task task);

    public TaskVo getRunTask(Task task);

    public int updateTimeOutTask();

}