package com.itheima.test;

import com.itheima.dao.IUserDao;
import com.itheima.domain.QueryVO;
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
 * MyBatis的增删查改
 */
public class MyBatisTest2 {
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
        //3.使用工厂生产SqlSession对象
        session = factory.openSession();
        //4.使用SqlSession创建Dao接口的代理对象
        userDao = session.getMapper(IUserDao.class);
    }

    @After
    public void destroy() throws IOException {
        //注意，需要提交事务
        session.commit();
        //6.释放资源
        session.close();
        inputStream.close();
    }

    /**
     * 测试查找
     */
    @Test
    public void testFind() {
        //5.使用代理对象执行方法
        List<User> resultList = userDao.findAll();
        for (User user : resultList) {
            System.out.println(user);
        }
    }

    /**
     * 测试保存
     */
    @Test
    public void testSave() {
        User user = new User();
        user.setUsername("小罗");
        user.setAddress("车陂南");
        user.setSex("女");
        user.setBirthday(new Date());
        System.out.println("保存之前：" + user);
        int userId = userDao.saveUser(user);
        System.out.println("保存之后：" + user);
        System.out.println("新增用户的Id：" + userId);
    }

    /**
     * 测试更新
     */
    @Test()
    public void testUpdate() {
        User user = new User();
        user.setId(50);
        user.setUsername("小罗2");
        user.setAddress("车陂南");
        user.setSex("女");
        user.setBirthday(new Date());
        userDao.updateUser(user);
    }

    /**
     * 测试删除
     */
    @Test
    public void testDelete() {
        userDao.deleteUser(50);
    }

    /**
     * 测试查询一个
     */
    @Test
    public void testFindOne() {
        User user = userDao.findById(48);
        System.out.println(user);
    }

    /**
     * 测试模糊查询
     */
    @Test
    public void testFindByName() {
        //由于sql语句中没有提供百分号，所以这里必须加上
//        List<User> users = userDao.findByName("%王%");
        //另外一种写法，百分号是自己嵌入sql中的，所以不需要加百分号，但是用拼接sql的方式，而不是占位符预编译的方式，会有sql注入问题
        List<User> users = userDao.findByName("王");
        for (User user : users) {
            System.out.println(user);
        }
    }

    /**
     * 测试查询总条数
     */
    @Test
    public void testFindTotal() {
        //由于sql语句中没有提供百分号，所以这里必须加上
        int count = userDao.findTotal();
        System.out.println("总条数：" + count);
    }

    /**
     * 测试QueryVO作为查询条件使用
     */
    @Test
    public void testFindByQueryVo() {
        QueryVO vo = new QueryVO();
        User user = new User();
        user.setUsername("%王%");
        vo.setUser(user);
        List<User> users = userDao.findUserByVo(vo);
        for (User model : users) {
            System.out.println(model);
        }
    }
}