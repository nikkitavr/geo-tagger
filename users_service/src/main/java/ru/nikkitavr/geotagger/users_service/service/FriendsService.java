package ru.nikkitavr.geotagger.users_service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.persistence.EntityManager;
import jakarta.ws.rs.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nikkitavr.geotagger.users_service.dto.UserResponseDto;
import ru.nikkitavr.geotagger.users_service.mapper.UserMapper;
import ru.nikkitavr.geotagger.users_service.model.RelationshipStatus;
import ru.nikkitavr.geotagger.users_service.model.User;
import ru.nikkitavr.geotagger.users_service.repository.RelationshipsRepository;
import ru.nikkitavr.geotagger.users_service.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class  FriendsService {

    private final UserRepository userRepository;
    private final RelationshipsRepository relationshipsRepository;
    private final UserMapper userMapper;
    private final EntityManager entityManager;
    private final RedisService redisService;


    public UserResponseDto getUserFriendById(long userId, long friendId){
        return userMapper.toUserDto(
                getFriends(userId)
                        .stream()
                        .filter(user -> user.getId() == friendId)
                        .findFirst()
                        .orElseThrow(NotFoundException::new)
        );
    }

    public List<UserResponseDto> getAllUserFriends(long userId){
        return userMapper.toUserDto(getFriends(userId));
    }

    public void deleteUserFriendById(long userId, long friendId) throws JsonProcessingException {
        relationshipsRepository.deleteById(
                userRepository
                        .findById(userId)
                        .orElseThrow(NotFoundException::new)
                        .getAllRelationships()
                        .stream()
                        .filter(r ->  r.getStatus() == RelationshipStatus.FRIEND &&
                                (r.getFriend().getId() == friendId ||
                                r.getUser().getId() == friendId))
                        .findFirst()
                        .orElseThrow(NotFoundException::new)
                        .getId()
        );
        entityManager.clear();
        redisService.saveUserFriendsId(friendId, getFriendsId(friendId));
        redisService.saveUserFriendsId(userId, getFriendsId(userId));
    }

    private List<User> getFriends(long userId){

        User user = userRepository.findById(userId)
                .orElseThrow(NotFoundException::new);

        return user.getFriends();
    }

    public List<Long> getFriendsId(long userId){
        List<User> friends = getFriends(userId);
        List<Long> friendsId = new ArrayList<>(friends.size());
        for (User friend:
             friends) {
            friendsId.add(friend.getId());
        }
        return friendsId;
    }


}
