package ru.nikkitavr.geotagger.geoservice.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisUsersGeoConfig {

    @Value("${spring.data.redisUsersGeo.host}")
    private String redisHost;
    @Value("${spring.data.redisUsersGeo.port}")
    private int redisPort;
    @Value("${spring.data.redisUsersGeo.database}")
    private int redisDBGeo;

    @Bean(name = "redisUsersGeoConnectionFactory")
    RedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration =
                new RedisStandaloneConfiguration(redisHost, redisPort);
        redisStandaloneConfiguration.setDatabase(redisDBGeo);
        return new LettuceConnectionFactory(redisStandaloneConfiguration);
    }

    @Bean(name = "redisTemplateGeo")
    public RedisTemplate<String, Object> redisTemplateGeo(@Qualifier("redisUsersGeoConnectionFactory") RedisConnectionFactory cf) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(cf);
        return template;
    }

}