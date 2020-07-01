package com.itheima.test;

import com.itheima.dao.IUserDao;
import com.itheima.domain.User;
import com.itheima.mybatis.io.Resources;
import com.itheima.mybatis.sqlsession.SqlSession;
import com.itheima.mybatis.sqlsession.SqlSessionFactory;
import com.itheima.mybatis.sqlsession.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * 自定义MyBatis框架测试
 */
public class CustomMyBatisTest {
    /**
     * MyBatis动态代理方式，生成Mapper实现
     */
    @Test
    public void test1() throws IOException {
        //1.读取配置文件
        InputStream inputStream = Resources.getResourceAsStream("SqlMapConfig.xml");
        //2.创建工厂
        SqlSessionFactory factory = new SqlSessionFactoryBuilder()
                .build(inputStream);
        //3.使用工厂生产SqlSession对象
        SqlSession session = factory.openSession();
        //4.使用SqlSession创建Dao接口的代理对象
        IUserDao userDao = session.getMapper(IUserDao.class);
        //5.使用代理对象执行方法
        List<User> resultList = userDao.findAll();
        for (User user : resultList) {
            System.out.println(user);
        }
        //6.释放资源
        session.close();
        inputStream.close();
    }
}