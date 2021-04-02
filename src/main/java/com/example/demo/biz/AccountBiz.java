package com.example.demo.biz;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.base.Exception.RpaException;
import com.example.demo.base.response.RpaResponse;
import com.example.demo.constant.CommonConstant;
import com.example.demo.constant.RedisPrefix;
import com.example.demo.dao.AccountMapper;
import com.example.demo.dao.AccountRecordMapper;
import com.example.demo.dao.RobotMapper;
import com.example.demo.entity.*;
import com.example.demo.util.JsonUtil;
import com.example.demo.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RQueue;
import org.redisson.api.RedissonClient;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.security.auth.login.Configuration;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class AccountBiz {

    @Resource
    private AccountMapper accountMapper;

    @Resource
    private RobotMapper robotMapper;

    @Resource
    private RedissonClient redisson;

    @Resource
    private AccountRecordMapper accountRecordMapper;

    private static final Snowflake snowflake = IdUtil.getSnowflake(1, 1);

    /**
     * 创建账号
     *
     * @param account
     */
    public void createAccount(Account account) {
        Long id = snowflake.nextId();
        account.setId(id);
        account.setCreateTime(new Date());
        account.setUpdateTime(new Date());
        accountMapper.insertSelective(account);
        // 添加到redis
        RQueue<Object> accountQ = redisson.getQueue(RedisPrefix.ACCOUNT + account.getWebsite());
        accountQ.add(JSONObject.toJSONString(account));
    }

    /**
     * 领用账号
     *
     * @param account
     * @return
     */
    public TakeAccountDto getAccountByWebSite(TakeAccountDto account) {

        // 根据机器人id 判断 是否已经领用过账号
        List<AccountRecord> recordList = accountRecordMapper.selectRobotUsed(account.getRobotId(), CommonConstant.USED);

        // 没有领用记录
        if (recordList.size() == 0) {
            // 根据网站名 在redis 队列里面获取一个账号
            RQueue<String> accountQ = redisson.getQueue(RedisPrefix.ACCOUNT + account.getWebsite());
            String accountJson = accountQ.poll();
            // 不为空的话更新状态
            if (StringUtils.isNotEmpty(accountJson)) {
                Account acc = JSONObject.parseObject(accountJson, Account.class);
                // 新增账号记录
                AccountRecord accountRecord = new AccountRecord();
                Long recordId = snowflake.nextId();
                accountRecord.setId(recordId);
                accountRecord.setRobotId(account.getRobotId());
                accountRecord.setTaskId(account.getTaskId());
                accountRecord.setAccountId(acc.getId());
                accountRecord.setUseTime(new Date());
                accountRecord.setStatus(CommonConstant.USED);
                accountRecordMapper.insertSelective(accountRecord);
                // 添加返回给机器人的信息
                account.setAccount(acc.getAccount());
                account.setPwd(acc.getPwd());
                account.setRecordId(recordId);
                return account;
            } else {
                throw new RpaException(RpaResponse.getErrorResult("30069"));
            }
        } else {
            AccountRecord record = recordList.get(0);
            // 有领用记录,根据记录的账号返回给机器人
            Account odlAcc = accountMapper.selectById(record.getAccountId());
            account.setAccount(odlAcc.getAccount());
            account.setPwd(odlAcc.getPwd());
            account.setRecordId(record.getId());
            return account;
        }
    }

    /**
     * 归还账号
     *
     * @param takeDto
     */
    public void restoreAccount(TakeAccountDto takeDto) {
        Account acc = accountMapper.selectByPrimaryKey(takeDto.getAccountId());
        if (acc == null) {
            // 归还了无效的账号
            throw new RpaException(RpaResponse.getErrorResult("30070"));
        }
        // 添加到redis
        RQueue<String> accountQ = redisson.getQueue(RedisPrefix.ACCOUNT + acc.getWebsite());
        // 查询redis 里面是否已经有该账号，避免重复归还
        boolean exist = accountQ.stream().anyMatch(ac -> {
            Account account = JsonUtil.parseObject(ac, Account.class);
            return account.getId().equals(takeDto.getAccountId());
        });
        // 已经存在
        if (!exist) {
            // 更新账号记录 、 归还账号
            AccountRecord accountRecord = new AccountRecord();
            accountRecord.setStatus(CommonConstant.UN_USED);
            accountRecord.setBackTime(new Date());
            accountRecord.setId(takeDto.getRecordId());
            accountRecordMapper.updateStatusById(accountRecord);
            accountQ.add(JSONObject.toJSONString(acc));
        }
    }

    /**
     * 删除账号
     *
     * @param account
     */
    public void deleteAccount(Account account) {
        Account acc = accountMapper.selectByPrimaryKey(account.getId());
        if (acc != null) {
            RQueue<String> accountQ = redisson.getQueue(RedisPrefix.ACCOUNT + acc.getWebsite());
            accountMapper.deleteAccount(account);
            // 根据id 删除队列里面的账号
            accountQ.removeIf(s -> s.contains(acc.getId().toString()));
        }
    }

    public void timeOutBackAccount(Long robotId) {
        // 判断是否有正在使用的账号但是没有归还
        List<AccountRecord> accountRecordList = accountRecordMapper.selectRobotUsed(robotId, CommonConstant.USED);
        if (accountRecordList.size() > 0) {
            AccountRecord accountRecord = accountRecordList.get(0);
            Account account = accountMapper.selectById(accountRecord.getAccountId());
            // 添加到redis
            RQueue<String> accountQ = redisson.getQueue(RedisPrefix.ACCOUNT + account.getWebsite());
            accountQ.add(JSONObject.toJSONString(account));
            // 手动归还
            accountRecord.setStatus(CommonConstant.UN_USED);
            accountRecordMapper.updateStatusById(accountRecord);
        }
    }

}
