package com.myapp.util;


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 读取配置
 * @author 竹
 * date 2017/11/5
 */
public class RedisConfig {
    public static final String server ;
    public static final String auth ;
    public static final int port;
    public static final int timeout;
    public static final int maxIdle;
    public static final int maxActive;

    static {
        InputStream resourceAsStream = RedisConfig.class.getResourceAsStream("/redis.properties");
        Properties properties = new Properties();
        try {
            properties.load(resourceAsStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        server = properties.getProperty("server");
        port = Integer.parseInt(properties.getProperty("port"));
        auth = properties.getProperty("password");
        timeout = Integer.parseInt(properties.getProperty("timeout"));
        maxActive = Integer.parseInt(properties.getProperty("maxActive"));
        maxIdle = Integer.parseInt(properties.getProperty("maxIdle"));
    }
}
