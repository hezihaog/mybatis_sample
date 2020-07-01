package com.itheima.mybatis.utils;

import com.itheima.mybatis.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 用于创建数据源的工具类
 */
public class DataSourceUtil {
    /**
     * 获取一个连接
     */
    public static Connection getConnection(Configuration configuration) {
        try {
            Class.forName(configuration.getDriver());
            return DriverManager.getConnection(configuration.getUrl(),
                    configuration.getUsername(),
                    configuration.getPassword());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
