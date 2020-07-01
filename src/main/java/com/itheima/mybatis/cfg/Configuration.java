package com.itheima.mybatis.cfg;

import java.util.HashMap;
import java.util.Map;

/**
 * 自定义MyBatis的配置类
 */
public class Configuration {
    /**
     * 数据库连接配置
     */
    private String driver;
    private String url;
    private String username;
    private String password;
    /**
     * 方法映射
     */
    private final Map<String, Mapper> mappers = new HashMap<String, Mapper>();

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Map<String, Mapper> getMappers() {
        return mappers;
    }

    public void setMappers(Map<String, Mapper> mappers) {
        //注意这里，每次追加，而不是覆盖
        this.mappers.putAll(mappers);
    }
}