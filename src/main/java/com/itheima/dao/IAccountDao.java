package com.itheima.dao;

import com.itheima.domain.Account;

import java.util.List;

/**
 * 账户Dao层接口
 */
public interface IAccountDao {
    /**
     * 查询所有账户
     */
    List<Account> findAll();

    /**
     * 查询所有账户，同时包含用户的信息
     */
    List<Account> findAllAccount();
}