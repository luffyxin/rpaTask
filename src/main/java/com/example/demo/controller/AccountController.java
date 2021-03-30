package com.example.demo.controller;


import com.example.demo.base.response.RpaResponse;
import com.example.demo.biz.AccountBiz;
import com.example.demo.entity.Account;
import com.example.demo.util.JsonUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/account")
public class AccountController {


    @Resource
    private AccountBiz accountBiz;


    @PostMapping("/any/get")
    public String getAccount(@RequestBody Account account){
        Account accountByWebSite = accountBiz.getAccountByWebSite(account);
        return JsonUtil.toJSONL2String(RpaResponse.getSuccessData(accountByWebSite));
    }

    @PostMapping("/create")
    public String createAccount(@RequestBody Account account){
        accountBiz.createAccount(account);
        return JsonUtil.toJSONL2String(RpaResponse.getSuccess());
    }

    @PostMapping("/delete")
    public String delteAccount(@RequestBody Account account){
        accountBiz.deleteAccount(account);
        return JsonUtil.toJSONL2String(RpaResponse.getSuccess());
    }

    /**
     * 归还账号
     * @param account
     * @return
     */
    @PostMapping("/restore")
    public String restoreAccount(@RequestBody Account account){
        accountBiz.restoreAccount(account);
        return JsonUtil.toJSONL2String(RpaResponse.getSuccess());
    }



}
