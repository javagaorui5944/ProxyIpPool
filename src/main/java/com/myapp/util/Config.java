package com.myapp.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 读取配置
 * @author 竹
 * date 2017/11/5
 */
public class Config {
    public static String server ;
    public static String auth ;
    public static int port;
    public static int timeout;
    public static int maxIdle;
    public static int maxActive;

    static {
        InputStream resourceAsStream = Config.class.getResourceAsStream("/redis.properties");
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
