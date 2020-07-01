package com.itheima.dao;

import com.itheima.domain.User;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 用户表持久层接口
 */
public interface IUserDao {
    /**
     * 查询所有用户
     */
//    @Select("select * from user")
    List<User> findAll();
}