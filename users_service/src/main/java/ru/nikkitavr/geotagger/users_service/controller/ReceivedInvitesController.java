package ru.nikkitavr.geotagger.users_service.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.nikkitavr.geotagger.users_service.dto.ReceivedInviteDto;
import ru.nikkitavr.geotagger.users_service.service.InviteService;
import ru.nikkitavr.geotagger.users_service.service.RedisService;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}/invites-received")
@AllArgsConstructor
public class ReceivedInvitesController {
    private final InviteService inviteService;
    private final RedisService redisService;

    @GetMapping
    public List<ReceivedInviteDto> getAllReceivedInvites(@PathVariable long userId){
        return inviteService.getAllReceivedInvites(userId);
    }

    @PostMapping("/{inviteId}/accept")
    public void acceptReceivedInvite(@PathVariable long userId, @PathVariable long inviteId) throws JsonProcessingException {
        inviteService.acceptReceivedInvite(userId, inviteId);
        redisService.saveUserFriendsId(userId);
        redisService.saveUserFriendsId(inviteService.getInviteOwnerId(inviteId));
    }

    @PostMapping("/{inviteId}/decline")
    public void declineReceivedInvite(@PathVariable long userId, @PathVariable long inviteId){
        inviteService.declineReceivedInvite(userId, inviteId);
    }
}
