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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 用户表的增删查改
 */
public class UserTest {
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
        //6.释放资源
        session.close();
        inputStream.close();
    }

    /**
     * 测试查找
     */
    @Test
    public void testFindAll() {
        //5.使用代理对象执行方法
        List<User> resultList = userDao.findAll();
        for (User user : resultList) {
            System.out.println(user);
        }
    }

    @Test
    public void testFindAllLazy() {
        List<User> resultList = userDao.findAllLazy();
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

    /**
     * 测试多种条件查询
     */
    @Test
    public void testFindUserByCondition() {
        User user = new User();
        user.setUsername("老王");
        user.setSex("男");
        List<User> resultList = userDao.findUserByCondition(user);
        if (resultList.isEmpty()) {
            System.out.println("没有查询到数据");
            return;
        }
        for (User model : resultList) {
            System.out.println(model);
        }
    }

    /**
     * 测试动态sql，foreach连接in
     */
    @Test
    public void testFindInIds() {
        QueryVO vo = new QueryVO();
        List<Integer> ids = new ArrayList<Integer>();
        ids.add(41);
        ids.add(42);
        ids.add(43);
        vo.setIds(ids);
        List<User> resultList = userDao.findUserInIds(vo);
        for (User user : resultList) {
            System.out.println(user);
        }
    }

    /**
     * 测试多对多关系查询
     */
    @Test
    public void testFindUserRoles() {
        List<User> resultList = userDao.findUserRoles();
        for (User user : resultList) {
            System.out.println(user);
        }
    }

    /**
     * 测试MayBatis的一级缓存，一级缓存指的是同一个SqlSession内查询时，可以复用缓存，而不需要重复查询
     *      注意：一级缓存区域，存放的是对象，所以查询出来的模型对象是同一个
     */
    @Test
    public void testFirstLevelCache() {
        User user1 = userDao.findById(41);
        System.out.println(user1);

        //session关闭，缓存就失效了
//        session.close();
//        session = factory.openSession();
//        userDao = session.getMapper(IUserDao.class);

        //获取清空缓存即可
//        session.clearCache();

        User user2 = userDao.findById(41);
        System.out.println(user2);
        System.out.println(user1 == user2);
    }

    /**
     * 测试同步缓存
     */
    @Test
    public void testClearCache() {
        User user1 = userDao.findById(41);
        System.out.println(user1);

        //更新用户信息，MyBatis调用SqlSession的修改、添加、删除、commit()、close()等方法时，就会清空一级缓存
        user1.setUsername("update user");
        user1.setAddress("北京市海淀区");
        userDao.updateUser(user1);

        User user2 = userDao.findById(41);
        System.out.println(user2);
        System.out.println(user1 == user2);
    }
}