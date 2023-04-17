package ru.nikkitavr.geotagger.users_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.nikkitavr.geotagger.users_service.dto.UserResponseDto;
import ru.nikkitavr.geotagger.users_service.service.FriendsService;

import java.util.List;

@RestController
@RequestMapping("/users/{user_id}/friends")
public class FriendsController {

    @Autowired
    public FriendsController(FriendsService friendsService) {
        this.friendsService = friendsService;
    }

    private final FriendsService friendsService;

    @GetMapping
    public List<UserResponseDto> getAllUserFriends(@PathVariable long user_id){
        return friendsService.getAllUserFriends(user_id);
    }

    @GetMapping("/{friend_id}")
    public UserResponseDto getUserFriend(@PathVariable long user_id, @PathVariable long friend_id){
        return friendsService.getUserFriendById(user_id, friend_id);
    }

    @DeleteMapping("/{friend_id}")
    public void deleteFriend(@PathVariable long user_id, @PathVariable long friend_id){
        friendsService.deleteUserFriendById(user_id, friend_id);
    }




}
