package com.example.demo.controller;


import com.example.demo.base.response.RpaResponse;
import com.example.demo.biz.AccountBiz;
import com.example.demo.entity.Account;
import com.example.demo.entity.TakeAccountDto;
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

    /**
     * 领用账号
     * @param takeDto robotId,taskId,website
     * @return account,pwd
     */
    @PostMapping("/any/get")
    public String getAccount(@RequestBody TakeAccountDto takeDto){
        TakeAccountDto accountByWebSite = accountBiz.getAccountByWebSite(takeDto);
        return JsonUtil.toJSONL2String(RpaResponse.getSuccessData(accountByWebSite));
    }

    @PostMapping("/create")
    public String createAccount(@RequestBody Account account){
        accountBiz.createAccount(account);
        return JsonUtil.toJSONL2String(RpaResponse.getSuccess());
    }

    @PostMapping("/delete")
    public String deleteAccount(@RequestBody Account account){
        accountBiz.deleteAccount(account);
        return JsonUtil.toJSONL2String(RpaResponse.getSuccess());
    }

    /**
     * 归还账号
     * @param account  recordId && accountId
     * @return ok
     */
    @PostMapping("/restore")
    public String restoreAccount(@RequestBody TakeAccountDto account){
        accountBiz.restoreAccount(account);
        return JsonUtil.toJSONL2String(RpaResponse.getSuccess());
    }



}
