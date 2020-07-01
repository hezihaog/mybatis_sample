package com.itheima.test;

import com.itheima.dao.IRoleDao;
import com.itheima.domain.Account;
import com.itheima.domain.Role;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * 角色表测试
 */
public class RoleTest {
    private InputStream inputStream;
    private SqlSession session;
    private IRoleDao roleDao;

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
        roleDao = session.getMapper(IRoleDao.class);
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
     * 测试多对多关系映射
     */
    @Test
    public void testFindAll() {
        List<Role> resultList = roleDao.findAll();
        for (Role role : resultList) {
            System.out.println(role);
        }
    }
}