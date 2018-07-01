package com.imooc.miaosha.redis;


import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Service("redisService")
public class RedisService {

    @Autowired
    private JedisPool jedisPool;

    /**
     * 从redis取数据
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T get(KeyPrefix keyPrefix,String key, Class<T> clazz) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            // 生成真正的key
            String readKey = keyPrefix.getPrefix()+key;
            String s = jedis.get(readKey);
            T t = stringToBean(s,clazz);
            return t;
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * redis设置值
     * @param key
     * @param value
     * @param <T>
     * @return
     */
    public <T> boolean set(KeyPrefix keyPrefix,String key, T value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String string = beanToString(value);
            if (string == null || string.length() <= 0) {
                return false;
            }
            // 生成真正的key
            String readKey = keyPrefix.getPrefix()+key;
            jedis.set(readKey, string);
            return true;
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 对象转成字符串
     * @param value
     * @param <T>
     * @return
     */
    private <T> String beanToString(T value) {
        if (value == null) {
            return null;
        }
        Class<?> clazz = value.getClass();
        if (clazz == int.class || clazz == Integer.class){
            return ""+value;
        }else if(clazz == String.class){
            return (String) value;
        }else if(clazz == long.class || clazz == Long.class){
            return ""+value;
        }else if(clazz == boolean.class || clazz == Boolean.class){
            return ""+value;
        }else{
            return JSON.toJSONString(value);
        }

    }

    /**
     * 字符串转成对象
     * @param s
     * @param <T>
     * @param clazz
     * @return
     */
    private <T> T stringToBean(String s,Class<T> clazz) {
        if (s == null || s.length() <= 0){
            return null;
        }

        if (clazz == int.class || clazz == Integer.class){
            return (T) Integer.valueOf(s);
        }else if(clazz == String.class){
            return (T) s;
        }else if(clazz == long.class || clazz == Long.class){
            return (T) Long.valueOf(s);
        }else if(clazz == boolean.class || clazz == Boolean.class){
            return (T) Boolean.valueOf(s);
        }else{
            return JSON.toJavaObject(JSON.parseObject(s),clazz);
        }
    }

    private void returnToPool(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }


}
