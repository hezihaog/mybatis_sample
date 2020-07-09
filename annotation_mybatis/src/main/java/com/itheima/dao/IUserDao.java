package com.itheima.dao;

import com.itheima.domain.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

/**
 * MyBatis注解练习
 * 注解一共有4个：
 * 1.@Select
 * 2.@Insert
 * 3.@Update
 * 4.@Delete
 */
//支持二级缓存，blocking表示是否开启，默认为false，改为true，则开启
@CacheNamespace(blocking = true)
public interface IUserDao {
    /**
     * 查询所有
     */
    @Select("select * from user")
    //如果实体和数据库字段不同，则可以使用@Results注解，配置映射关系，column属性为数据库字段，property属性为实体字段
    @Results(id = "userMap", value = {
            @Result(
                    id = true,
                    column = "id",
                    property = "userId"
            ),
            @Result(
                    column = "username",
                    property = "userName"
            ),
            @Result(
                    column = "address",
                    property = "userAddress"
            ),
            @Result(
                    column = "sex",
                    property = "userSex"
            ),
            @Result(
                    column = "birthday",
                    property = "userBirthday"
            ),
            //配置一对多的关联
            @Result(
                    //column为调用AccountDao层方法传递的参数
                    column = "id",
                    //property为封装到实体的字段的名称
                    property = "accounts",
                    many = @Many(
                            select = "com.itheima.dao.IAccountDao.findAccountByUid",
                            fetchType = FetchType.LAZY
                    )
            )
    })
    List<User> findAll();

    /**
     * 保存用户
     */
    @Insert("insert into user(username,address,sex,birthday) values(#{username},#{address},#{sex},#{birthday})")
    @ResultMap(value = {"userMap"})
    void saveUser(User user);

    /**
     * 更新用户
     */
    @Update("update user set username=#{username}, address=#{address},sex=#{sex},birthday=#{birthday} where id = #{id}")
    @ResultMap(value = {"userMap"})
    void updateUser(User user);

    /**
     * 删除用户
     */
    @Delete("delete from user where id = #{id}")
    @ResultMap(value = {"userMap"})
    void deleteUser(int id);

    /**
     * 根据用户Id，查询用户信息
     *
     * @param id 用户Id
     */
    @Select("select * from user where id = #{id}")
    @ResultMap(value = {"userMap"})
    User findById(int id);

    /**
     * 根据名称模糊查询，用户列表
     *
     * @param username 用户名
     */
    @Select("select * from user where username like #{username}")
    @ResultMap(value = {"userMap"})
    //或者使用字符串拼接的方式
//    @Select("select * from user where username like '%${value}%'")
    List<User> findUserByName(String username);

    /**
     * 查询总数
     */
    @Select("select count(*) from user")
    @ResultMap(value = {"userMap"})
    int findTotal();
}