package com.example.demo.biz;

import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

@Component
public class KeyExpireObserved extends Observable {

    @Resource
    private List<Observer> observerList;

    @PostConstruct
    public void observerRegister(){
        // 注册观察者
        observerList.forEach(this::addObserver);
    }

    /**
     * 通知观察者
     */
    public void notifyObserver(String message){
        try{
            this.setChanged();
            this.notifyObservers(message);
        }catch (InternalError e){
            e.printStackTrace();
        }
    }


}
