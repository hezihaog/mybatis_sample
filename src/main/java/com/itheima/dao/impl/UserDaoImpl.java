package com.itheima.dao.impl;

import com.itheima.dao.IUserDao;
import com.itheima.domain.User;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

/**
 * 自己实现用户表的持久层实现
 */
public class UserDaoImpl implements IUserDao {
    private SqlSessionFactory factory;

    public UserDaoImpl(SqlSessionFactory factory) {
        this.factory = factory;
    }

    public List<User> findAll() {
        SqlSession session = factory.openSession();
        //statement是配置文件中，命名空间加上方法名来组合的
        List<User> resultList = session.selectList("com.itheima.dao.IUserDao.findAll");
        session.close();
        return resultList;
    }
}