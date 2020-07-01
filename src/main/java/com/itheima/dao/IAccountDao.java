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

    /**
     * 查询所有用户，并使用懒加载方式，加载账户包含的用户信息
     */
    List<Account> findAllAccountLazy();

    /**
     * 根据用户Id查询账户
     */
    List<Account> findAccountByUid(Integer id);
}