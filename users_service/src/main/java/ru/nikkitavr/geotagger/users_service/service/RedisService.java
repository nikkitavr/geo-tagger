package ru.nikkitavr.geotagger.users_service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
@AllArgsConstructor
public class RedisService {

    private final RedisTemplate<String, String> redisTemplate;
    private final FriendsService friendsService;
    private final ObjectMapper objectMapper;

    private final EntityManager entityManager;

    public void saveUserFriendsId (long userId) throws JsonProcessingException {
        entityManager.clear();

        redisTemplate.opsForValue().set(
                new String(Long.toString(userId).getBytes(), StandardCharsets.UTF_8),
                objectMapper.writeValueAsString(
                        friendsService.getFriendsId(userId)
                )
        );

    }

    public List<Long> getUserFriendsId (long userId) throws JsonProcessingException {
        String friendsIdJson = redisTemplate
                .opsForValue()
                .get(new String(Long.toString(userId).getBytes(), StandardCharsets.UTF_8));
        List<Long> friendsId = objectMapper.readValue(friendsIdJson, new TypeReference<List<Long>>() {
        });
        System.out.println(friendsIdJson);
        for (Long id:
             friendsId) {
            System.out.println(id);
        }
        return friendsId;
    }
}
