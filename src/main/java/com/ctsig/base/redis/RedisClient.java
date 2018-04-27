package com.ctsig.base.redis;

import com.ctsig.base.enums.ResultCodeEnum;
import com.ctsig.base.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * redis相关API
 *
 * @author wangan
 * @date 2017/3/1
 */
@Service
@Slf4j
public class RedisClient {

    @Autowired
    private JedisPool jedisPool;

    /**
     * @param key
     * @return
     */
    public Boolean exists(String key) {
        Boolean result = null;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.exists(key);
        } catch (Exception e) {
            log.error("redis exists error, key: {}", key, e);
            throw new BaseException(ResultCodeEnum.RedisException);
        } finally {
            closeRedis(jedis);
        }
        return result;
    }

    /**
     * @param key
     * @return
     */
    public String get(String key) {
        String result = null;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.get(key);
        } catch (Exception e) {
            log.error("redis get error, key: {}", key, e);
            throw new BaseException(ResultCodeEnum.RedisException);
        } finally {
            closeRedis(jedis);
        }
        return result;
    }

    /**
     * @param key
     * @param value
     * @return
     */
    public String set(String key, String value) {
        String result = null;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.set(key, value);
        } catch (Exception e) {
            log.error("redis set error, key: {}", key, e);
            throw new BaseException(ResultCodeEnum.RedisException);
        } finally {
            closeRedis(jedis);
        }
        return result;
    }


    /**
     * @param key
     */
    public void del(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.del(key);
        } catch (Exception e) {
            log.error("redis del error, key: {}", key, e);
            throw new BaseException(ResultCodeEnum.RedisException);
        } finally {
            closeRedis(jedis);
        }
    }


    /**
     * @param key
     * @param seconds
     * @return
     */
    public Long expire(String key, int seconds) {
        Long result = null;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.expire(key, seconds);
        } catch (Exception e) {
            log.error("redis expire error, key: {}", key, e);
            throw new BaseException(ResultCodeEnum.RedisException);
        } finally {
            closeRedis(jedis);
        }
        return result;
    }


    private void closeRedis(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }
}