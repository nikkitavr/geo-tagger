package ru.nikkitavr.geotagger.geoservice.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

@Configuration
public class RedisFriendsConfig {
    @Value("${spring.data.redisFriends.host}")
    private String redisHost;
    @Value("${spring.data.redisFriends.port}")
    private int redisPort;
    @Value("${spring.data.redisFriends.database}")
    private int redisDBFriends;

    @Bean(name="redisFriendsDbConnectionFactory")
    RedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration =
                new RedisStandaloneConfiguration(redisHost, redisPort);
        redisStandaloneConfiguration.setDatabase(redisDBFriends);
        return new LettuceConnectionFactory(redisStandaloneConfiguration);
    }

    @Bean(name = "redisTemplateFriends")
    public RedisTemplate<String, Object> redisTemplateFriends(@Qualifier("redisFriendsDbConnectionFactory") RedisConnectionFactory cf) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(cf);
        return template;
    }

    @Bean(name = "stringRedisTemplateFriends")
    public StringRedisTemplate stringRedisTemplateFriends(@Qualifier("redisFriendsDbConnectionFactory") RedisConnectionFactory cf) {
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(cf);
        return template;
    }

}
