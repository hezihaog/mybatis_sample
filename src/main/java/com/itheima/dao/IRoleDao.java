package com.itheima.dao;

import com.itheima.domain.Role;

import java.util.List;

/**
 * 角色表的Dao层接口
 */
public interface IRoleDao {
    /**
     * 查询所有角色
     */
    List<Role> findAll();
}