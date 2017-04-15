package redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisPubSub;

/**
 * Created by gaorui on 17/4/14.
 */
public class redis {


    public static  void main(String args[]) {
        JedisPool pool = new JedisPool(new JedisPoolConfig(), "127.0.0.1", 6379);

        Jedis jedis = pool.getResource();
        JedisPubSub jedisPubSub = new JedisPubSub() {
            @Override
            public void onUnsubscribe(String channel, int number) {
                System.out.println("channel: "+channel);
                System.out.println("number :"+number);
            }
            @Override
            public void onSubscribe(String channel, int number) {
                System.out.println("channel: "+channel);
                System.out.println("number :"+number);
            }
            @Override
            public void onPUnsubscribe(String arg0, int arg1) {
            }
            @Override
            public void onPSubscribe(String arg0, int arg1) {
            }
            @Override
            public void onPMessage(String arg0, String arg1, String arg2) {
            }
            @Override
            public void onMessage(String channel, String msg) {
                System.out.println("收到频道 : 【" + channel +" 】的消息 ：" + msg);
            }
        };
        jedis.subscribe(jedisPubSub, new String[]{"channel1","channel2"});
        pool.returnResource(jedis);
    }



}
