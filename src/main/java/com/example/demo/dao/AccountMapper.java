package com.example.demo.dao;

import com.example.demo.entity.Account;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface AccountMapper extends Mapper<Account> {


    List<Account> selectByWebSite(String website);

    List<Account> selectAll();

    void deleteAccount(Account account);

    void updateStatusBatch(List<Account> accounts);

    Account selectById(Long id);

}