package com.nick.springbootinit.manager;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class RedisRateLimiterManagerTest {

    @Resource
    private RedisLimiterManager redisLimiterManager;

    @Test
    public void tryLimiter() {
        String userId = "1";
        for (int i = 0; i < 5; i++) {
            redisLimiterManager.rateLimit(userId);
        }
    }
}
