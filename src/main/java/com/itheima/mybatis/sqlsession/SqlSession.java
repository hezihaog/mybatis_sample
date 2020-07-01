package com.itheima.mybatis.sqlsession;

/**
 * 自定义MyBatis中，和数据库交互的核心类，它可以创建dao的代理对象
 */
public interface SqlSession {
    /**
     * 根据Dao的接口，创建它的实现类
     *
     * @param daoInterfaceClass Dao的接口
     */
    <T> T getMapper(Class<T> daoInterfaceClass);

    /**
     * 释放资源
     */
    void close();
}