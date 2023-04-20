package ru.nikkitavr.geotagger.users_service.controller;

import jakarta.security.auth.message.AuthException;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.nikkitavr.geotagger.users_service.dto.SentInviteRequestDto;
import ru.nikkitavr.geotagger.users_service.dto.SentInviteResponseDto;
import ru.nikkitavr.geotagger.users_service.security.AuthUser;
import ru.nikkitavr.geotagger.users_service.security.UserDetailsImpl;
import ru.nikkitavr.geotagger.users_service.service.InviteService;

import java.util.List;

@RestController
@RequestMapping("/me/invites-sent")
@AllArgsConstructor
public class SentInvitesController {
    private final InviteService inviteService;

    @PostMapping
    public void sendInvite(@RequestBody SentInviteRequestDto sentInvitesDto) throws AuthException {
        inviteService.sendInvite(AuthUser.getId(), sentInvitesDto);
    }

    @GetMapping
    public List<SentInviteResponseDto> getAllSentInvites() throws AuthException {
        return inviteService.getAllOwnedInvites(AuthUser.getId());
    }

    @DeleteMapping("/{inviteId}")
    public void deleteSentInvite(@PathVariable long inviteId) throws AuthException {
        inviteService.deleteOwnedInvite(AuthUser.getId(), inviteId);
    }
}
