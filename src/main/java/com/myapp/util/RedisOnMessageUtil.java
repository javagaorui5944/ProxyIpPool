package com.myapp.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.myapp.crawler4j.Controller;
import org.apache.log4j.Logger;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import static com.myapp.util.RedisConfig.*;

import java.math.BigDecimal;

/**
 * Created by gaorui on 17/4/21.
 */
public class RedisOnMessageUtil {
    private static Logger logger = Logger.getLogger(Controller.class);

    static JedisPool pool = new JedisPool(new JedisPoolConfig(), server, port);

    public static void Push(String area, String ip, int port) {
        try {
            Jedis jedis = pool.getResource();
            area = area.replaceAll("[a-zA-Z]", "").replaceAll("[?]", "").replaceAll(" ", "").trim();
            String strResult = HttpUtil.sendGet("http://maps.google.cn/maps/api/geocode/json", "address=" + area + "&sensor=false");
            JSONObject jsonObject = JSON.parseObject(strResult);
            JSONArray jsonArray = (JSONArray) jsonObject.get("results");
            if (jsonArray.size() > 0) {

                JSONObject jsonObject1 = (JSONObject) jsonArray.get(0);
                JSONObject jsonObject2 = (JSONObject) jsonObject1.get("geometry");
                JSONObject jsonObject3 = (JSONObject) jsonObject2.get("viewport");
                JSONObject jsonObject4 = (JSONObject) jsonObject3.get("northeast");
                BigDecimal lat = null;
                BigDecimal lng = null;
                if (jsonObject4.get("lat") instanceof BigDecimal) {
                    lat = (BigDecimal) jsonObject4.get("lat");
                    lng = (BigDecimal) jsonObject4.get("lng");
                    jedis.publish("channel1", ip + ":" + port + "#" + area + "@" + lat + "$" + lng);
                }

            }
        }catch (Exception e){

            logger.error(e.getMessage());
        }

    }
}
