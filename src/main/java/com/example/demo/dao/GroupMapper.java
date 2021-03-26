package com.example.demo.dao;

import com.example.demo.entity.Group;
import com.example.demo.entity.GroupVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface GroupMapper extends Mapper<Group> {

    List<Group> findNameList();


    List<Integer> getGroupListGroupById();

    List<GroupVo> getGroupList(@Param("skipNum") Integer skipNum,@Param("pageSize") Integer pageSize);

    int selectCountByName(String name);

    int updateGroup(Group group);


    Group selectById(Long id);

    int logicDelete(Long id);

}