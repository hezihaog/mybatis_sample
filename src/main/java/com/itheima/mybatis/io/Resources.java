package com.itheima.mybatis.io;

import java.io.InputStream;

/**
 * 自定义加载
 */
public class Resources {
    /**
     * 将配置文件加载为一个输入流
     *
     * @param filePath 配置文件
     */
    public static InputStream getResourceAsStream(String filePath) {
        return Resources.class.getClassLoader()
                .getResourceAsStream(filePath);
    }
}