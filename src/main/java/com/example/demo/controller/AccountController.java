package com.example.demo.controller;


import com.example.demo.base.response.ApiResult;
import com.example.demo.base.response.RpaResponse;
import com.example.demo.biz.AccountBiz;
import com.example.demo.entity.Account;
import com.example.demo.util.JsonUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/account")
public class AccountController {


    @Resource
    private AccountBiz accountBiz;


    @PostMapping("/any/get")
    public String getAccount(Account account){
        Account accountByWebSite = accountBiz.getAccountByWebSite(account.getWebsite());
        return JsonUtil.toJSONL2String(RpaResponse.getSuccessData(accountByWebSite));
    }

}
