package com.myapp.redis;

import com.myapp.proxy.HttpProxy;
import redis.clients.jedis.Jedis;

import java.util.Set;

/**
 *
 * @author 竹
 * date 2017/11/5
 */
public class JedisUtils {

    private static void release(Jedis jedis) {
        jedis.close();
    }

    private static Jedis getJedis() {
        return JedisPoolFactory.getInstance().getResource();
    }

    public static void setProxyIp(HttpProxy httpProxy) {
        Jedis jedis = getJedis();
        jedis.sadd("httpProxy", JsonUtils.toString(httpProxy));
        release(jedis);
    }

    public static Set<String> getProxyIp() {
        Jedis jedis = getJedis();
        Set<String> set = jedis.keys("*");
        release(jedis);
        return set;
    }

}