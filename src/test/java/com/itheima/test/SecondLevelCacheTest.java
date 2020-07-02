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
 * MyBatis的二级缓存测试
 */
public class SecondLevelCacheTest {
    private InputStream inputStream;
    private SqlSessionFactory factory;

    @Before
    public void init() throws IOException {
        //1.读取配置文件
        inputStream = Resources.getResourceAsStream("SqlMapConfig.xml");
        //2.创建工厂
        factory = new SqlSessionFactoryBuilder()
                .build(inputStream);
    }

    @After
    public void destroy() throws IOException {
        inputStream.close();
    }

    /**
     * 测试MayBatis的二级缓存，同一个SqlSessionFactory生产出来的SqlSession共享Factory的缓存区域
     *
     * 配置步骤：
     *  1.在配置文件中，让MyBatis支持二级缓存（SqlMapConfig.xml）
     *  2.在当前映射文件中支持二级缓存（IUserDao.xml）
     *  3.让当前操作支持二级缓存（在select标签中配置）
     *
     *  注意：二级缓存区域存放的是数据，而不是对象（和一级缓存不同）
     */
    @Test
    public void testSecondLevelCache() {
        SqlSession session1 = factory.openSession();
        IUserDao dao1 = session1.getMapper(IUserDao.class);
        User user1 = dao1.findById(41);
        System.out.println(user1);
        session1.close();

        SqlSession session2 = factory.openSession();
        IUserDao dao2 = session2.getMapper(IUserDao.class);
        User user2 = dao2.findById(41);
        System.out.println(user2);
        session2.close();

        System.out.println(user1 == user2);
    }
}