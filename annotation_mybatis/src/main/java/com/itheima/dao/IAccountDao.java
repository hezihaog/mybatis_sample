package com.itheima.dao;

import com.itheima.domain.Account;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

public interface IAccountDao {
    /**
     * 查询所有账户，并且获取每个账户下的所属的用户信息
     */
    @Select("select * from account")
    @Results(id = "accountMap", value = {
            @Result(
                    id = true,
                    column = "id",
                    property = "id"
            ),
            @Result(
                    column = "uid",
                    property = "uid"
            ),
            @Result(
                    column = "money",
                    property = "money"
            ),
            //配置一对一的关联
            @Result(
                    //column为调用User的单表查询时传的参数
                    column = "uid",
                    //property为封装到实体的字段的名称
                    property = "user",
                    one = @One(
                            //select为去单表查询的方法
                            select = "com.itheima.dao.IUserDao.findById",
                            //多对一，使用立即加载
                            fetchType = FetchType.EAGER
                    )
            )
    })
    List<Account> findAll();

    /**
     * 根据Id查询账户信息
     */
    @Select("select * from account where uid = #{userId}")
    Account findAccountByUid(int userId);
}