package com.example.demo.controller;


import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.example.demo.base.response.ApiParam;
import com.example.demo.base.response.ApiResult;
import com.example.demo.base.response.RpaResponse;
import com.example.demo.biz.UserBiz;
import com.example.demo.entity.User;
import com.example.demo.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserBiz userBiz;

    @PostMapping("/logout")
    public String logout(String token){
        StpUtil.logout();
        return JsonUtil.toJSONL2String(new ApiParam<>(ApiResult.getOkResult()));
    }

    @PostMapping("/login")
    public String login(@RequestBody User user){
        SaTokenInfo tokenInfo = userBiz.login(user);
        return JsonUtil.toJSONL2String(new ApiParam<>(tokenInfo, ApiResult.getOkResult()));
    }


    @PostMapping("/robot/login")
    public String robotLogin(@RequestBody User user){
        SaTokenInfo tokenInfo = userBiz.login(user);
        return JsonUtil.toJSONL2String(RpaResponse.getSuccessData(tokenInfo));
    }

    @GetMapping("/info")
    public String getInfo(String token){
        User user = userBiz.getUserByToken(token);
        // todo 修改写死的角色
        List<String> roles = new ArrayList<>();
        roles.add("admin");
        user.setRoles(roles);
        return JsonUtil.toJSONL2String(new ApiParam<>(user, ApiResult.getOkResult()));
    }

    @PostMapping("/create")
    public String createUser(@RequestBody User user){
        userBiz.createUser(user);
        return JsonUtil.toJSONL2String(new ApiParam<>(ApiResult.getOkResult()));
    }

}
