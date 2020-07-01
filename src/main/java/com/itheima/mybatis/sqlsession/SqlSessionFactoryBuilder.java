package com.itheima.mybatis.sqlsession;

import com.itheima.mybatis.cfg.Configuration;
import com.itheima.mybatis.sqlsession.defaults.DefaultSqlSessionFactory;
import com.itheima.mybatis.utils.XMLConfigBuilder;

import java.io.InputStream;

/**
 * 用于创建一个SqlSessionFactory对象
 */
public class SqlSessionFactoryBuilder {
    /**
     * 根据配置文件的输入流，构建一个SqlSessionFactory工厂对象
     *
     * @param config 配置文件输入流
     */
    public SqlSessionFactory build(InputStream config) {
        //加载配置文件为一个Configuration对象
        Configuration configuration = XMLConfigBuilder.loadConfiguration(config);
        return new DefaultSqlSessionFactory(configuration);
    }
}