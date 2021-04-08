package com.example.demo.biz;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.example.demo.base.Exception.BizException;
import com.example.demo.base.response.ApiResult;
import com.example.demo.base.response.ResResultCode;
import com.example.demo.dao.UserMapper;
import com.example.demo.entity.User;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Date;

@Service
public class UserBiz {


    @Resource
    private UserMapper userMapper;

    private static final Snowflake snowflake = IdUtil.getSnowflake(1, 1);

    public SaTokenInfo login(User user){
        User dbUser = userMapper.selectByUsername(user.getUsername());
        if(dbUser == null){
            // 账号不存在
            throw new BizException(ApiResult.getErrorResult("40301"));
        }
        String password = SaSecureUtil.md5(user.getPassword());
        if(password.equals(dbUser.getPassword())){
            StpUtil.setLoginId(dbUser.getId());
            return StpUtil.getTokenInfo();
        }else{
            // 密码错误
            throw new BizException(ApiResult.getErrorResult("40302"));
        }
    }

    public User getUserByToken(String token){
        Object userId = StpUtil.getLoginIdByToken(token);
        if(null == userId){
            throw new BizException(ApiResult.getErrorResult(ResResultCode.EXPIRED_ACCESS_TOKEN));
        }else {
            return userMapper.selectById(Long.parseLong( userId.toString()));
        }
    }

    public void createUser(User user){
        Long id = snowflake.nextId();
        user.setId(id);
        user.setCreateTime(new Date());
        String password = SaSecureUtil.md5(user.getPassword());
        user.setPassword(password);
        userMapper.insertSelective(user);
    }

}
