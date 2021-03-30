package com.example.demo.biz;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.base.Exception.RpaException;
import com.example.demo.base.response.RpaResponse;
import com.example.demo.constant.CommonConstant;
import com.example.demo.constant.RedisPrefix;
import com.example.demo.dao.AccountMapper;
import com.example.demo.dao.RobotMapper;
import com.example.demo.entity.Account;
import com.example.demo.entity.Robot;
import com.example.demo.entity.RobotVo;
import com.example.demo.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RQueue;
import org.redisson.api.RedissonClient;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AccountBiz {

    @Resource
    private AccountMapper accountMapper;

    @Resource
    private RobotMapper robotMapper;

    @Resource
    private RedissonClient redisson;

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
    public Account getAccountByWebSite(Account account) {
        // 根据网站名 在redis 队列里面获取一个账号
        RQueue<String> accountQ = redisson.getQueue(RedisPrefix.ACCOUNT + account.getWebsite());
        String accountJson = accountQ.poll();
        // 不为空的话更新状态
        if (StringUtils.isNotEmpty(accountJson)) {
            Account acc = JSONObject.parseObject(accountJson, Account.class);
            acc.setRobotId(account.getRobotId());
            acc.setTakeTime(new Date());
            acc.setUpdateTime(new Date());
            accountMapper.updateStatusById(acc);
            return acc;
        } else {
            throw new RpaException(RpaResponse.getErrorResult("30069"));
        }
    }

    /**
     * 归还账号
     *
     * @param account
     */
    public void restoreAccount(Account account) {
        Account acc = accountMapper.selectByPrimaryKey(account.getId());
        acc.setRobotId(null);
        acc.setTakeTime(null);
        acc.setUpdateTime(new Date());
        accountMapper.updateStatusById(acc);
        // 添加到redis
        RQueue<Object> accountQ = redisson.getQueue(RedisPrefix.ACCOUNT + acc.getWebsite());
        accountQ.add(JSONObject.toJSONString(account));
    }

    /**
     * 删除账号
     *
     * @param account
     */
    public void deleteAccount(Account account) {
        Account acc = accountMapper.selectByPrimaryKey(account.getId());
        RQueue<String> accountQ = redisson.getQueue(RedisPrefix.ACCOUNT + acc.getWebsite());
        accountMapper.deleteAccount(account);
        // 根据id 删除队列里面的账号
        accountQ.removeIf(s -> s.contains(acc.getId().toString()));
    }

    /**
     * 查看数据库中领用账号信息是否正确
     *
     */
    @Scheduled(cron = "0 0/5 * * * ?")
    public void fixAccount() {
        log.info("查询账号领用情况是否无误");
        List<Account> accounts = accountMapper.selectUsedAccount();
        Robot robot = new Robot();
        robot.setStatus(CommonConstant.ROBOT_STATUS_BUSY);
        List<RobotVo> robotVoList = robotMapper.selectRobotList(robot);
        // 查找被领用的账号，但是机器人没有使用的情况
        List<Account> accountList = accounts.stream().filter(acc -> {
            return robotVoList.stream().anyMatch(bot -> {
                return bot.getId().equals(acc.getRobotId());
            });
        }).collect(Collectors.toList());

        if (accountList.size() > 0) {
            // 批量更新账号
            accountMapper.updateStatusBatch(accountList);
            // 账号重新添加到redis 队列
            for (Account acc : accountList) {
                RQueue<String> accountQ = redisson.getQueue(RedisPrefix.ACCOUNT + acc.getWebsite());
                accountQ.add(JSONObject.toJSONString(acc));
            }
        }


    }


}
