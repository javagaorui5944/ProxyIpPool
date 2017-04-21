package com.myapp.redis;

import com.myapp.proxy.HttpProxy;
import redis.clients.jedis.Jedis;

/**
 * Created by gaorui on 17/1/7.
 */
public class RedisStorage {

    //连接redis服务器 127.0.0.1:6379
    private static Jedis jedis = new Jedis("127.0.0.1", 6379);

    //权限认证
    //jedis.auth("admin");
    private RedisStorage() {

    }

    public static Jedis getInstance() {

        return jedis;
    }

    public static void setProxyIp(HttpProxy httpProxy) {
        jedis.sadd("httpProxy",httpProxy.getProxy().toString());
    }

    /* public static void setProxyIp(String s) {

        jedis.lpush("mykey", s);
    }
    */
/*
    public static void main(String args[]){

        String str = jedis("httpProxy");
        System.out.print(str);
    }*/
}
