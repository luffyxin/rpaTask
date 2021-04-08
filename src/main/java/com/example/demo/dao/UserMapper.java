package com.example.demo.dao;

import com.example.demo.entity.User;
import tk.mybatis.mapper.common.Mapper;

public interface UserMapper extends Mapper<User> {

    User selectByUsername(String username);

    User selectById(Long  id);


}