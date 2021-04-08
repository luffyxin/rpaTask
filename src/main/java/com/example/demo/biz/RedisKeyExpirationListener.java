package com.example.demo.biz;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

@Service
public class RedisKeyExpirationListener extends KeyExpirationEventMessageListener {


    @Resource
    private KeyExpireObserved keyExpireObserved;


    public RedisKeyExpirationListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        //获取过期的key
        String expireKey = message.toString();
        keyExpireObserved.notifyObserver(expireKey);
    }

}
