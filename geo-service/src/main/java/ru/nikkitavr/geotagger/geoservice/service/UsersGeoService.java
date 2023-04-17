package ru.nikkitavr.geotagger.geoservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import ru.nikkitavr.geotagger.geoservice.dto.ClientDataMessageDto;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static ru.nikkitavr.geotagger.geoservice.utils.RedisStringBuilder.buildString;

@Component
@AllArgsConstructor
public class UsersGeoService {
    RedisTemplate<String, Object> redisTemplateGeo;
    RedisTemplate<String, String> redisTemplateFriends;
    ObjectMapper objectMapper;
    ServiceConstants serviceConstants;

//    public UsersGeoService(
//            @Qualifier("redisTemplateGeo") RedisTemplate<String, Object> redisTemplateGeo,
//            @Qualifier("redisTemplateFriends") RedisTemplate<String, Object> redisTemplateFriends,
//            ObjectMapper objectMapper) {
//
//        this.redisTemplateGeo = redisTemplateGeo;
//        this.redisTemplateFriends = redisTemplateFriends;
//        this.objectMapper = objectMapper;
//    }



    public void saveGeo(ClientDataMessageDto clientDataMessageDto){
        redisTemplateGeo.opsForGeo().add(
                buildString(serviceConstants.geoSet),
                clientDataMessageDto.getData(),
                buildString(clientDataMessageDto.getUserId())
        );
    }

    public void startStreamFriendsGeo(long userId, WebSocketSession session) throws IOException, InterruptedException {
        Thread clientThread =
                new Thread( () -> {
                    try {
                        streamGeo(userId, session);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                });
        clientThread.start();
    }

    private void streamGeo(long userId, WebSocketSession session) throws IOException, InterruptedException {
        while(true) {
            session.sendMessage(new TextMessage(
                    objectMapper.writeValueAsString(
                            getFriendsGeo(userId)
                    )
            ));
            Thread.sleep(serviceConstants.threadLatency);
        }
    }

    private Map<String, Point> getFriendsGeo(long userId) throws JsonProcessingException {
        List<String> friendsId = getFriendsId(userId);
        List<Point> friendsPositions = null;//
        try {
            friendsPositions =
                    redisTemplateGeo.opsForGeo()
                            .position(buildString(serviceConstants.geoSet), friendsId.toArray());
        } catch(Exception e) {
            e.printStackTrace();
        }

        Map<String, Point> friendsPositionAsMap =
                IntStream.range(0, friendsId.size())
                        .boxed()
                        .collect(Collectors.toMap(
                                friendsId::get,
                                friendsPositions::get
                        ));
        return friendsPositionAsMap;
    }

    private List<String> getFriendsId(long userId) throws JsonProcessingException {
        String friendsIdJson = redisTemplateFriends
                .opsForValue()
                .get(new String(Long.toString(userId).getBytes(), StandardCharsets.UTF_8));

        List<String> friendsId = objectMapper.readValue(friendsIdJson, new TypeReference<List<String>>() {
        });

//        for (int i = 0; i < friendsId.size(); i++) {
//            friendsId.set(i, buildString(friendsId.get(i)));
//        }

        System.out.println(friendsIdJson);
        for (String id:
                friendsId) {
            System.out.println(id);
        }
        return friendsId;
    }

}

//    public void saveUserFriendsId (long userId) throws JsonProcessingException {
//        entityManager.clear();
//
//
//
//    }
//
//    public List<Long> getUserFriendsId (long userId) throws JsonProcessingException {
//        String friendsIdJson = redisTemplate
//                .opsForValue()
//                .get(new String(Long.toString(userId).getBytes(), StandardCharsets.UTF_8));
//        List<Long> friendsId = objectMapper.readValue(friendsIdJson, new TypeReference<List<Long>>() {
//        });
//        System.out.println(friendsIdJson);
//        for (Long id:
//                friendsId) {
//            System.out.println(id);
//        }
//        return friendsId;
//    }