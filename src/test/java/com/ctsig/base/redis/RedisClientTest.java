package com.ctsig.base.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * @author wangan
 * @date 2018/4/27
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class RedisClientTest {

    @Resource
    private RedisClient redisClient;

    @Test
    public void exists() {
        Boolean hi = redisClient.exists("hi");
        System.out.println(hi);
    }
}