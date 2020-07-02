package com.itheima.test;

import com.itheima.dao.IUserDao;
import com.itheima.domain.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * MyBatis注解测试
 *
 * 注意，同一个Dao，要么选择注解，要么选择xml，不能同时存在，不然会报错
 */
public class MyBatisAnnoTest {
    public static void main(String[] args) throws IOException {
        //获取配置文件输入流
        InputStream in = Resources.getResourceAsStream("SqlMapConfig.xml");
        //根据配置文件输入流创建Factory
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
        //根据Factory生产session
        SqlSession session = factory.openSession();
        //使用session获取Dao的代理对象
        IUserDao dao = session.getMapper(IUserDao.class);
        //执行Dao的方法
        List<User> users = dao.findAll();
        for (User user : users) {
            System.out.println(user);
        }
        //释放资源
        session.close();
        in.close();
    }
}