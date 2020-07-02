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
import java.util.Date;
import java.util.List;

/**
 * 测试MyBatis注解开发的CRUD
 */
public class AnnotationCRUDTest {
    private InputStream inputStream;
    private SqlSession session;
    private IUserDao userDao;

    @Before
    public void init() throws IOException {
        //1.读取配置文件
        inputStream = Resources.getResourceAsStream("SqlMapConfig.xml");
        //2.创建工厂
        SqlSessionFactory factory = new SqlSessionFactoryBuilder()
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
        //6.释放资源
        session.close();
        inputStream.close();
    }

    /**
     * 测试注解方式保存
     */
    @Test
    public void testSave() {
        User user = new User();
        user.setUsername("子和");
        user.setAddress("广州市天河区");
        user.setSex("男");
        user.setBirthday(new Date());
        userDao.saveUser(user);
    }

    @Test
    public void testUpdateUser() {
        User user = new User();
        user.setId(54);
        user.setUsername("子和2");
        user.setAddress("广州市黄埔区");
        user.setSex("男");
        user.setBirthday(new Date());
        userDao.updateUser(user);
    }

    @Test
    public void testDeleteUser() {
        userDao.deleteUser(54);
    }

    @Test
    public void testFindOne() {
        User user = userDao.findById(53);
        System.out.println(user);
    }

    @Test
    public void testFindUserByName() {
        List<User> users = userDao.findUserByName("%王%");
        //使用字符串拼接方式，则不需要传百分号
//        List<User> users = userDao.findUserByName("王");
        for (User user : users) {
            System.out.println(user);
        }
    }

    @Test
    public void testFindTotal() {
        int total = userDao.findTotal();
        System.out.println(total);
    }
}