package com.example.demo.controller;


import cn.dev33.satoken.stp.StpUtil;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.base.response.ApiParam;
import com.example.demo.base.response.ApiResult;
import com.example.demo.entity.User;
import com.example.demo.util.JsonUtil;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @PostMapping("/login")
    public String login(@RequestBody User user){
        String token = "";
        if("admin".equals(user.getUsername())){
            token = "admin-token";
        }else{
            token = "editor-token";
        }
        StpUtil.setLoginId(user.getUsername());
        return JsonUtil.toJSONL2String(new ApiParam<>(token, ApiResult.getOkResult()));
    }

    @GetMapping("/info")
    public String getInfo(String token){
        User user = new User();
        user.setAvatar("https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        user.setName("super admin");
        user.setIntroduction("I am a super administrator");
        List<String> roles = new ArrayList<>();
        roles.add("admin");
        user.setRoles(roles);
        return JsonUtil.toJSONL2String(new ApiParam<>(user, ApiResult.getOkResult()));
    }



}
