package com.itheima.test;

import com.itheima.dao.IUserDao;
import com.itheima.domain.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

/**
 * 注解方式，使用二级缓存
 */
public class SecondLevelCacheTest {
    private InputStream inputStream;
    private SqlSessionFactory factory;
    private SqlSession session;
    private IUserDao userDao;

    @Before
    public void init() throws IOException {
        //1.读取配置文件
        inputStream = Resources.getResourceAsStream("SqlMapConfig.xml");
        //2.创建工厂
        factory = new SqlSessionFactoryBuilder()
                .build(inputStream);
        //3.使用工厂生产SqlSession对象，autoCommit设置为true时，为自动提交事务，默认为false，一般我们都手动控制事务
//        session = factory.openSession(true);
        session = factory.openSession();
        //4.使用SqlSession创建Dao接口的代理对象
        userDao = session.getMapper(IUserDao.class);
    }

    @After
    public void destroy() throws IOException {
        //注意，如果openSession()，没有传autoCommit参数，或者设置为false，则需要自己手动提交事务
        session.commit();
        inputStream.close();
    }

    /**
     * 二级缓存测试
     *
     * 开启二级缓存步骤
     * 1.MyBatis配置文件中开启二级缓存
     * 2.Dao接口中，加上@CacheNamespace(blocking = true)注解
     */
    @Test
    public void testFindOne() {
        User user1 = userDao.findById(53);
        System.out.println(user1);

        //关闭一级缓存
        session.close();
        session = factory.openSession();
        userDao = session.getMapper(IUserDao.class);

        User user2 = userDao.findById(53);
        System.out.println(user1);

        //一级缓存，默认都是打开的，所以2次查询的实体是同一个
        System.out.println(user1 == user2);
    }
}