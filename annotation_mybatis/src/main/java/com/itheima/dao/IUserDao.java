package com.itheima.dao;

import com.itheima.domain.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * MyBatis注解练习
 * 注解一共有4个：
 * 1.@Select
 * 2.@Inser
 * 3.@Update
 * 4.@Delete
 */
public interface IUserDao {
    /**
     * 查询所有
     */
    @Select("select * from user")
    List<User> findAll();

    /**
     * 保存用户
     */
    @Insert("insert into user(username,address,sex,birthday) values(#{username},#{address},#{sex},#{birthday})")
    void saveUser(User user);

    /**
     * 更新用户
     */
    @Update("update user set username=#{username}, address=#{address},sex=#{sex},birthday=#{birthday} where id = #{id}")
    void updateUser(User user);

    /**
     * 删除用户
     */
    @Delete("delete from user where id = #{id}")
    void deleteUser(int id);

    /**
     * 根据用户Id，查询用户信息
     *
     * @param id 用户Id
     */
    @Select("select * from user where id = #{id}")
    User findById(int id);

    /**
     * 根据名称模糊查询，用户列表
     *
     * @param username 用户名
     */
    @Select("select * from user where username like #{username}")
    //或者使用字符串拼接的方式
//    @Select("select * from user where username like '%${value}%'")
    List<User> findUserByName(String username);

    /**
     * 查询总数
     */
    @Select("select count(*) from user")
    int findTotal();
}