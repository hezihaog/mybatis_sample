package com.itheima.mybatis.sqlsession;

/**
 * 工厂，生产SqlSession对象
 */
public interface SqlSessionFactory {
    /**
     * 打开一个新的SqlSession
     */
    SqlSession openSession();
}