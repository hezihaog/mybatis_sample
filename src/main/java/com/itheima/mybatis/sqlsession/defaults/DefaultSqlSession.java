package com.itheima.mybatis.sqlsession.defaults;

import com.itheima.mybatis.cfg.Configuration;
import com.itheima.mybatis.cfg.Mapper;
import com.itheima.mybatis.sqlsession.SqlSession;
import com.itheima.mybatis.utils.DataSourceUtil;
import com.itheima.mybatis.utils.Executor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

/**
 * SqlSession的默认实现类
 */
public class DefaultSqlSession implements SqlSession {
    private Configuration configuration;
    /**
     * 数据库连接
     */
    private Connection connection;

    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
        this.connection = DataSourceUtil.getConnection(configuration);
    }

    public <T> T getMapper(Class<T> daoInterfaceClass) {
        //动态代理创建Dao层实现类
        return (T) Proxy.newProxyInstance(daoInterfaceClass.getClassLoader(),
                new Class[]{daoInterfaceClass},
                new MapperProxy(configuration.getMappers(), connection));
    }

    public void close() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    /**
     * 处理Mapper中的方法的处理器，就是调用selectList方法
     */
    private static class MapperProxy implements InvocationHandler {
        /**
         * 数据库连接
         */
        private Connection connection;

        /**
         * Map的key是全限定类名+方法名
         */
        private Map<String, Mapper> mappers;

        public MapperProxy(Map<String, Mapper> mappers, Connection connection) {
            this.mappers = mappers;
            this.connection = connection;
        }

        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            //1.获取方法名
            String methodName = method.getName();
            //2.获取方法的声明类的名称
            String className = method.getDeclaringClass().getName();
            //3.组合key
            String key = className + "." + methodName;
            //4.获取mappers中的Mapper对象
            Mapper mapper = mappers.get(key);
            //5.判断mapper是否存在
            if (mapper == null) {
                throw new IllegalArgumentException("传入的参数有误");
            }
            //调用工具类，查询
            return new Executor().selectList(mapper, connection);
        }
    }
}