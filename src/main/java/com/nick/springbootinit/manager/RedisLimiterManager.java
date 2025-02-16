package com.nick.springbootinit.manager;

import com.nick.springbootinit.common.ErrorCode;
import com.nick.springbootinit.exception.BusinessException;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.Duration;

@Service
public class RedisLimiterManager {

    @Resource
    private RedissonClient redissonClient;

    public void rateLimit(String key) {
        RRateLimiter rRateLimiter = redissonClient.getRateLimiter(key);
        // Set the rate limiter with a rate of 5 requests per second
        rRateLimiter.trySetRate(RateType.OVERALL, 5, Duration.ofSeconds(1));

        // Acquire a token for per request
        boolean allowed = rRateLimiter.tryAcquire(1);
        if (allowed) {

        } else {
            throw new BusinessException(ErrorCode.TOO_MANY_REQUEST, "Too many requests in a second");
        }
    }
}
