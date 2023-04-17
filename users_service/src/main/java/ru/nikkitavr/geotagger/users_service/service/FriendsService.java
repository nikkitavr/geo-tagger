package ru.nikkitavr.geotagger.users_service.service;

import jakarta.ws.rs.NotFoundException;
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

import java.util.List;

@Service
public class  FriendsService {

    @Autowired
    public FriendsService(UserRepository userRepository, RelationshipsRepository relationshipsRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.relationshipsRepository = relationshipsRepository;
        this.userMapper = userMapper;
    }

    private final UserRepository userRepository;
    private final RelationshipsRepository relationshipsRepository;

    private final UserMapper userMapper;

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

    public void deleteUserFriendById(long userId, long friendId){
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
    }

    private List<User> getFriends(long userId){

        User user = userRepository.findById(userId)
                .orElseThrow(NotFoundException::new);

        return user.getFriends();
    }


}
