package com.nick.springbootinit.config;

import lombok.Data;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "spring.redis")
@Data
public class RedissonConfig {

    private Integer database;
    private String host;
    private String password;
    private String port;

    @Bean
    public RedissonClient getRedissonClient() {

        Config config = new Config();
        config.useSingleServer()
                .setDatabase(database)
                .setPassword(password)
                .setAddress("redis://" + host + ":" + port);

        RedissonClient redisson = Redisson.create(config);
        return redisson;
    }
}
