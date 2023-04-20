package ru.nikkitavr.geotagger.users_service.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.security.auth.message.AuthException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import ru.nikkitavr.geotagger.users_service.dto.UserResponseDto;
import ru.nikkitavr.geotagger.users_service.security.AuthUser;
import ru.nikkitavr.geotagger.users_service.security.UserDetailsImpl;
import ru.nikkitavr.geotagger.users_service.service.FriendsService;
import ru.nikkitavr.geotagger.users_service.service.RedisService;

import java.util.List;

@RestController
@RequestMapping("/me/friends")
@AllArgsConstructor
public class FriendsController {
    private final FriendsService friendsService;
    private final RedisService redisService;


    @GetMapping
    public List<UserResponseDto> getAllUserFriends() throws AuthException {
        return friendsService.getAllUserFriends(AuthUser.getId());
    }

    @GetMapping("/{friend_id}")
    public UserResponseDto getUserFriend(@PathVariable long friend_id) throws AuthException {
        return friendsService.getUserFriendById(AuthUser.getId(), friend_id);
    }

    @DeleteMapping("/{friend_id}")
    public void deleteFriend( @PathVariable long friend_id) throws JsonProcessingException, AuthException {
        friendsService.deleteUserFriendById(AuthUser.getId(), friend_id);
        redisService.saveUserFriendsId(AuthUser.getId());
        redisService.saveUserFriendsId(friend_id);
    }




}
