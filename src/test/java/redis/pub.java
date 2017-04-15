package redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by gaorui on 17/4/14.
 */
public class pub {




    public static  void main(String args[]) {
        JedisPool pool = new JedisPool(new JedisPoolConfig(), "127.0.0.1", 6379);
        Jedis jedis = pool.getResource();
        long i = jedis.publish("channel1", "channel1的朋友们，你们好吗？亲");
        System.out.println(i+" 个订阅者接受到了 channel1 消息");
        i = jedis.publish("channel2", "你好呀，亲");
        System.out.println(i+" 个订阅者接受到了 channel2 消息");
        pool.returnResource(jedis);
    }
}
