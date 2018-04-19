package com.ctsig.base.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wangan
 * @date 2017/3/1
 * @EnableCaching 启用缓存
 */
@Configuration
@EnableCaching
@Slf4j
public class RedisConfig extends CachingConfigurerSupport {


    @Bean
    public KeyGenerator smpkeyGenerator() {
        return (target, method, params) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(target.getClass().getName());
            sb.append(method.getName());
            for (Object obj : params) {
                sb.append(obj.toString());
            }
            return sb.toString();
        };
    }

    /**
     * 缓存管理器
     *
     * @param redisTemplate
     * @return
     */
    @Bean
    public CacheManager cacheManager(RedisTemplate redisTemplate) {
        RedisCacheManager rcm = new RedisCacheManager(redisTemplate);
        //设置缓存过期时间
        // rcm.setDefaultExpiration(60);//秒
        //设置value的过期时间
        Map<String, Long> map = new HashMap(10);
        map.put("test", 60L);
        rcm.setExpires(map);
        return rcm;
    }

    /**
     * redis模板操作类,类似于jdbcTemplate的一个类
     * 虽然CacheManager也能获取到Cache对象，但是操作起来没有那么灵活
     * 这里在扩展下：RedisTemplate这个类不见得很好操作，我们可以在进行扩展一个我们
     * 自己的缓存类，比如：RedisStorage类
     *
     * @param factory 通过Spring进行注入，参数在application.properties进行配置
     * @return
     */
    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory) {
        StringRedisTemplate template = new StringRedisTemplate(factory);
        //定义key序列化方式
        //Long类型会出现异常信息;需要我们上面的自定义key生成策略，一般没必要
        //RedisSerializer<String> redisSerializer = new StringRedisSerializer();
        //定义value的序列化方式
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);

        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }

    @Bean(name = "spring.redis")
    @Autowired
    public JedisPool jedisPool(@Qualifier("spring.redis.pool") JedisPoolConfig config,
                               @Value("${spring.redis.host}") String host,
                               @Value("${spring.redis.port}") int port,
                               @Value("${spring.redis.password}") String password) {
        return new JedisPool(config, host, port, 0, password);

    }

    @Bean(name = "spring.redis.pool")
    public JedisPoolConfig jedisPoolConfig(@Value("${spring.redis.pool.maxTotal}") int maxTotal,
                                           @Value("${spring.redis.pool.maxIdle}") int maxIdle,
                                           @Value("${spring.redis.pool.maxWaitMillis}") int maxWaitMillis) {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(maxTotal);
        config.setMaxIdle(maxIdle);
        config.setMaxWaitMillis(maxWaitMillis);
        return config;
    }


    /**
     * redis异常处理
     *
     * @return
     */
    @Bean
    @Override
    public CacheErrorHandler errorHandler() {

        CacheErrorHandler cacheErrorHandler = new CacheErrorHandler() {

            @Override
            public void handleCacheGetError(RuntimeException e, Cache cache, Object o) {
                log.error("handleCacheGetError: {}", cache.getName());

            }

            @Override
            public void handleCachePutError(RuntimeException e, Cache cache, Object o, Object o1) {
                log.error("handleCachePutError: {}", cache.getName());
            }

            @Override
            public void handleCacheEvictError(RuntimeException e, Cache cache, Object o) {
                log.error("handleCacheEvictError: {}", cache.getName());
            }

            @Override
            public void handleCacheClearError(RuntimeException e, Cache cache) {
                log.error("handleCacheClearError: {}", cache.getName());
            }
        };
        return cacheErrorHandler;
    }
}
