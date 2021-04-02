package com.example.demo.biz;

import com.example.demo.constant.CommonConstant;
import com.example.demo.constant.RedisPrefix;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Observable;
import java.util.Observer;
@Component
public class AccountExpireObserver implements Observer {

    @Resource
    private AccountBiz accountBiz;

    @Override
    public void update(Observable o, Object arg) {
        Long robotId =Long.parseLong(arg.toString().split("-")[1]);
        // 机器人任务连接过期
        if(arg.toString().startsWith(RedisPrefix.ROBOT_TASK)){
            accountBiz.timeOutBackAccount(robotId);
        }

    }


}
