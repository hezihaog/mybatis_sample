package com.itheima.dao.impl;

import com.itheima.dao.IUserDao;
import com.itheima.domain.QueryVO;
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

    public List<User> findAllLazy() {
        return null;
    }

    public int saveUser(User user) {
        SqlSession session = factory.openSession();
        session.insert("com.itheima.dao.IUserDao.saveUser", user);
        //提交事务
        session.commit();
        //释放资源
        session.close();
        return user.getId();
    }

    public void updateUser(User user) {
        SqlSession session = factory.openSession();
        session.update("com.itheima.dao.IUserDao.updateUser", user);
        //提交事务
        session.commit();
        //释放资源
        session.close();
    }

    public void deleteUser(int userId) {
        SqlSession session = factory.openSession();
        session.update("com.itheima.dao.IUserDao.deleteUser", userId);
        //提交事务
        session.commit();
        //释放资源
        session.close();
    }

    public User findById(int userId) {
        SqlSession session = factory.openSession();
        User user = session.selectOne("com.itheima.dao.IUserDao.findById", userId);
        //提交事务
        session.commit();
        //释放资源
        session.close();
        return user;
    }

    public List<User> findByName(String username) {
        SqlSession session = factory.openSession();
        List<User> userList = session.selectList("com.itheima.dao.IUserDao.findByName", username);
        //提交事务
        session.commit();
        //释放资源
        session.close();
        return userList;
    }

    public int findTotal() {
        SqlSession session = factory.openSession();
        Integer count = session.selectOne("com.itheima.dao.IUserDao.findTotal");
        //提交事务
        session.commit();
        //释放资源
        session.close();
        return count;
    }

    public List<User> findUserByVo(QueryVO vo) {
        SqlSession session = factory.openSession();
        List<User> userList = session.selectList("com.itheima.dao.IUserDao.findUserByVo", vo);
        //提交事务
        session.commit();
        //释放资源
        session.close();
        return userList;
    }

    public List<User> findUserByCondition(User user) {
        return null;
    }

    public List<User> findUserInIds(QueryVO vo) {
        return null;
    }

    public List<User> findUserRoles() {
        return null;
    }
}