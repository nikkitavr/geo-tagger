package ru.nikkitavr.geotagger.users_service.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.security.auth.message.AuthException;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.nikkitavr.geotagger.users_service.dto.ReceivedInviteDto;
import ru.nikkitavr.geotagger.users_service.security.AuthUser;
import ru.nikkitavr.geotagger.users_service.security.UserDetailsImpl;
import ru.nikkitavr.geotagger.users_service.service.InviteService;
import ru.nikkitavr.geotagger.users_service.service.RedisService;

import java.util.List;

@RestController
@RequestMapping("/me/invites-received")
@AllArgsConstructor
public class ReceivedInvitesController {
    private final InviteService inviteService;
    private final RedisService redisService;

    @GetMapping
    public List<ReceivedInviteDto> getAllReceivedInvites() throws AuthException {
        return inviteService.getAllReceivedInvites(AuthUser.getId());
    }

    @PostMapping("/{inviteId}/accept")
    public void acceptReceivedInvite(@PathVariable long inviteId) throws JsonProcessingException, AuthException {
        inviteService.acceptReceivedInvite(AuthUser.getId(), inviteId);
        redisService.saveUserFriendsId(AuthUser.getId());
        redisService.saveUserFriendsId(inviteService.getInviteOwnerId(inviteId));
    }

    @PostMapping("/{inviteId}/decline")
    public void declineReceivedInvite(@PathVariable long inviteId) throws AuthException {
        inviteService.declineReceivedInvite(AuthUser.getId(), inviteId);
    }
}
