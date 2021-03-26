package com.example.demo.dao;

import com.example.demo.entity.GroupVo;
import com.example.demo.entity.Robot;
import com.example.demo.entity.RobotVo;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface RobotMapper extends Mapper<Robot> {

    public Robot selectById(Long id);

    public List<RobotVo> selectRobotList(Robot robot);

    public int logicDelete(Long id);

    public int updateRobotGroup(GroupVo groupVo);

    public int clearGroupRobot(GroupVo groupVo);

    public int updateRobot(Robot robot);

}