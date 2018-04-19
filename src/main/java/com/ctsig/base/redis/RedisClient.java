package com.ctsig.base.redis;

import com.ctsig.base.consts.ProgramConst;
import com.ctsig.base.exception.RedisClientException;
import com.google.common.base.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * redis api
 *
 * @author wangan
 * @date 2017/3/1
 */
@Service
@Slf4j
public class RedisClient {

    @Autowired
    JedisPool jedisPool;

    /**
     * @return
     */
    public Optional<Jedis> jedis() throws RedisClientException {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
        } catch (Exception e) {
            String errorMsg = "Get jedis instance error.";
            log.error(errorMsg, e);
            throw new RedisClientException(errorMsg, e);
        }
        return Optional.of(jedis);
    }

    /**
     * @param jedis
     */
    public void returnJedis(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }

    /**
     * @param key
     * @return
     */
    public Boolean exists(String key) throws RedisClientException {
        Boolean result = null;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.exists(key);
        } catch (Exception e) {
            log.error("Redis EXISTS error, key: {}", key, e);
            throw new RedisClientException(e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return result;
    }

    public Boolean exists(byte[] key) {
        Boolean result = null;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.exists(key);
        } catch (Exception e) {
            log.error("Redis byte EXISTS error.", e);
            throw new RedisClientException(e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        if (jedis != null) {
            jedis.close();
        }
        return result;
    }

    /**
     * @param key
     * @return
     */

    public String get(String key) throws RedisClientException {
        String result = null;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.get(key);
        } catch (Exception e) {
            log.error("Redis GET error, key: {}", key, e);
            throw new RedisClientException(e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return result;
    }

    public byte[] get(byte[] key) throws RedisClientException {
        if (key == null) {
            return null;
        }
        byte[] result = null;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.get(key);
        } catch (Exception e) {
            log.error("Redis byte GET error.", e);
            throw new RedisClientException(e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return result;
    }

    /**
     * @param key
     * @param value
     * @return
     * @throws RedisClientException
     */
    public String set(String key, String value) throws RedisClientException {
        String result = null;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.set(key, value);
        } catch (Exception e) {
            log.error("Redis set error, key: {}", key, e);
            throw new RedisClientException(e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return result;
    }

    public String set(byte[] key, byte[] value) throws RedisClientException {
        String result = null;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.set(key, value);
        } catch (Exception e) {
            log.error("Redis byte SET error.", e);
            throw new RedisClientException(e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return result;
    }

    /**
     * @param key
     * @param inc
     * @return
     * @throws RedisClientException
     */
    public Long incrBy(String key, long inc) throws RedisClientException {
        Long result = 0L;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.incrBy(key, inc);
        } catch (Exception e) {
            log.error("Redis INCRBY error, key: {}", key, e);
            throw new RedisClientException(e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return result;
    }

    /**
     * @param key
     * @param dec
     * @return
     * @throws RedisClientException
     */
    public Long decrBy(String key, long dec) throws RedisClientException {
        Long result = 0L;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.decrBy(key, dec);
        } catch (Exception e) {
            log.error("Redis DECRBY error, key: {}", key, e);
            throw new RedisClientException(e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return result;
    }

    /**
     * @param keys
     * @return
     * @throws RedisClientException
     */
    public List<String> mget(final String... keys) throws RedisClientException {
        List<String> result = null;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.mget(keys);
        } catch (Exception e) {
            log.error("Redis MGET error,keys {}", keys, e);
            throw new RedisClientException(e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return result;
    }

    public List<byte[]> mget(final byte[]... keys) throws RedisClientException {
        List<byte[]> result = null;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.mget(keys);
        } catch (Exception e) {
            log.error("Redis byte MGET error.", e);
            throw new RedisClientException(e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return result;
    }

    /**
     * @param key
     * @throws RedisClientException
     */
    public void del(String key) throws RedisClientException {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.del(key);
        } catch (Exception e) {
            log.error("Redis DEL error, key: {}", key, e);
            throw new RedisClientException(e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public void del(byte[] key) throws RedisClientException {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.del(key);
        } catch (Exception e) {
            log.error("Redis byte DEL error.", e);
            throw new RedisClientException(e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * @param pattern
     * @return
     * @throws RedisClientException
     */
    public Set<String> keys(String pattern) throws RedisClientException {
        Set<String> result = null;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.keys(pattern);
        } catch (RuntimeException e) {
            log.error("Redis KEYS error, pattern: {}", pattern, e);
            throw new RedisClientException(e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return result;
    }

    public Set<byte[]> keys(byte[] pattern) throws RedisClientException {
        Set<byte[]> result = null;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.keys(pattern);
        } catch (RuntimeException e) {
            log.error("Redis byte KEYS error.", e);
            throw new RedisClientException(e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return result;
    }

    /**
     * @param key
     * @param seconds
     * @return
     * @throws RedisClientException
     */
    public Long expire(String key, int seconds) throws RedisClientException {
        Long result = null;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.expire(key, seconds);
        } catch (Exception e) {
            log.error("Redis EXPIRE error, key: {}", key, e);
            throw new RedisClientException(e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return result;
    }

    public Long expire(byte[] key, int seconds) throws RedisClientException {
        Long result = null;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.expire(key, seconds);
        } catch (Exception e) {
            log.error("Redis byte EXPIRE error.}", key, e);
            throw new RedisClientException(e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return result;
    }

    /**
     * @param key
     * @param value
     * @param seconds
     * @return
     * @throws RedisClientException
     */
    public String setex(String key, String value, int seconds) throws RedisClientException {
        String result = null;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.setex(key, seconds, value);
        } catch (Exception e) {
            log.error("Redis SETEX error, key: {}", key, e);
            throw new RedisClientException(e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return result;
    }

    public String setex(byte[] key, byte[] value, int seconds) throws RedisClientException {
        String result = null;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.setex(key, seconds, value);
        } catch (Exception e) {
            log.error("Redis byte SETEX error.", e);
            throw new RedisClientException(e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return result;
    }

    /**
     * @param key
     * @return
     * @throws RedisClientException
     */
    public Long ttl(String key) throws RedisClientException {
        Long result = null;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.ttl(key);
        } catch (Exception e) {
            log.error("Redis TTL error, key: {}", key, e);
            throw new RedisClientException(e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return result;
    }

    /**
     * @param key
     * @param value
     * @param seconds
     * @return
     * @throws RedisClientException
     */
    public String setnxex(String key, String value, int seconds) throws RedisClientException {
        String result = null;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.set(key, value, ProgramConst.NX, ProgramConst.EX, seconds);
        } catch (Exception e) {
            log.error("Redis SETNX error, key: {}", key, e);
            throw new RedisClientException(e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return result;
    }

    /**
     * @param key
     * @param value
     * @return
     * @throws RedisClientException
     */
    public Long setnx(String key, String value) throws RedisClientException {
        Long result = null;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.setnx(key, value);
        } catch (Exception e) {
            log.error("Redis SETNX error, key: {}", key, e);
            throw new RedisClientException(e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return result;
    }

    /**
     * @param key
     * @param value
     * @return
     * @throws RedisClientException
     */
    public String getset(String key, String value) throws RedisClientException {
        String result = null;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.getSet(key, value);
        } catch (Exception e) {
            log.error("Redis GETSET error, key: {}", key, e);
            throw new RedisClientException(e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return result;
    }

    /**
     * @param key
     * @param field
     * @return
     * @throws RedisClientException
     */
    public String hget(String key, String field) throws RedisClientException {
        String result = null;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.hget(key, field);
        } catch (Exception e) {
            log.error("Redis HGET error, key: {} field: {}", key, field, e);
            throw new RedisClientException(e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return result;
    }

    public void sadd(String key, String[] members) throws RedisClientException {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.sadd(key, members);
        } catch (Exception e) {
            log.error("Redis SADD error, key: {} member: {}", key, members[0]);
            throw new RedisClientException(e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public void srem(String key, String[] members) throws RedisClientException {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.srem(key, members);
        } catch (Exception e) {
            log.error("Redis SREM error, key: {} , member: {}", key, members[0]);
            throw new RedisClientException(e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public void smove(String srckey, String dstkey, String member) throws RedisClientException {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.smove(srckey, dstkey, member);
        } catch (Exception e) {
            log.error("Redis SMOVE error, from {} to {} , member: {}", srckey, dstkey, member);
            throw new RedisClientException(e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public Set<String> smembers(String key) throws RedisClientException {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.smembers(key);
        } catch (Exception e) {
            log.error("Redis SMEMBERS error, key: {}", key);
            throw new RedisClientException(e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public byte[] hget(byte[] key, byte[] field) throws RedisClientException {
        byte[] result = null;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.hget(key, field);
        } catch (Exception e) {
            log.error("Redis byte HGET error.", e);
            throw new RedisClientException(e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return result;
    }

    /**
     * @param key
     * @param field
     * @param value
     * @return
     * @throws RedisClientException
     */
    public Long hset(String key, String field, String value) throws RedisClientException {
        Long result = null;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.hset(key, field, value);
        } catch (Exception e) {
            log.error("Redis HSET error, key: {} field: {}", key, field, e);
            throw new RedisClientException(e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return result;
    }

    public Map<String, String> hgetall(String key) throws RedisClientException {
        Map<String, String> result = null;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.hgetAll(key);
        } catch (Exception e) {
            log.error("Redis HGETALL error, key: {}", key, e);
            throw new RedisClientException(e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return result;
    }

}
