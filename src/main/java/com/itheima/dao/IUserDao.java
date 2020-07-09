package com.itheima.dao;

import com.itheima.domain.QueryVO;
import com.itheima.domain.User;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 用户表持久层接口
 */
public interface IUserDao {
    /**
     * 查询所有用户，同时获取到用户下的所有账户信息
     */
    List<User> findAll();

    /**
     * 查询所有用户，懒加载，获取到用户下的所有账户信息
     */
    List<User> findAllLazy();

    /**
     * 保存用户
     */
    int saveUser(User user);

    /**
     * 更新用户
     */
    void updateUser(User user);

    /**
     * 删除用户
     *
     * @param userId 用户Id
     */
    void deleteUser(int userId);

    /**
     * 按用户Id查询用户信息
     */
    User findById(int userId);

    /**
     * 根据名称，模糊查询用户信息
     */
    List<User> findByName(String username);

    /**
     * 查询总用户数
     */
    int findTotal();

    /**
     * 根据QueryVO查询用户信息
     */
    List<User> findUserByVo(QueryVO vo);

    /**
     * 根据条件查询用户信息
     *
     * @param user 查询条件
     */
    List<User> findUserByCondition(User user);

    /**
     * 根据QueryVO中的id集合，查询多个用户信息
     */
    List<User> findUserInIds(QueryVO vo);

    /**
     * 查询所有用户信息和角色信息
     */
    List<User> findUserRoles();
}