package com.example.demo.biz;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.base.Exception.BizException;
import com.example.demo.base.Exception.RpaException;
import com.example.demo.base.response.ApiResult;
import com.example.demo.base.response.RpaResponse;
import com.example.demo.constant.RedisPrefix;
import com.example.demo.dao.AccountMapper;
import com.example.demo.entity.Account;
import org.redisson.api.RList;
import org.redisson.api.RQueue;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.RedisConnectionUtils;
import org.springframework.data.redis.support.collections.RedisList;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AccountBiz {

    @Resource
    private AccountMapper accountMapper;

    @Resource
    private RedissonClient redisson;

    private static final Snowflake snowflake = IdUtil.getSnowflake(1, 1);


    public void createAccount(Account account){
        Long id = snowflake.nextId();
        account.setId(id);
        account.setCreateTime(new Date());
        account.setUpdateTime(new Date());
        accountMapper.insertSelective(account);
        // 添加到redis
        RQueue<Object> accountQ = redisson.getQueue(RedisPrefix.ACCOUNT + account.getWebsite());
        accountQ.add(JSONObject.toJSONString(account));
    }

    public Account getAccountByWebSite(String website){

        RQueue<String> accountQ = redisson.getQueue(RedisPrefix.ACCOUNT + website);
        String accountJson = accountQ.poll();
        Account account = JSONObject.parseObject(accountJson, Account.class);

        List<Account> accounts = accountMapper.selectByWebSite(website);
        Optional<Account> any = accounts.stream().findAny();

        if(any.isPresent()){
            return any.get();
        }else {
            throw new RpaException(RpaResponse.getErrorResult("30069"));
        }

    }

}
