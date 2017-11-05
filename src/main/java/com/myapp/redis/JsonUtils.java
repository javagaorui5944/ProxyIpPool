package com.myapp.redis;

import com.alibaba.fastjson.JSON;

/**
 *
 * @author 竹
 * date 2017/11/5
 */
public class JsonUtils {

    public static String toString(Object object){
        return JSON.toJSONString(object);
    }

    public static Object toObject(String json,Class<?> clazz){
       return JSON.parseObject(json,clazz);
    }



}
