package ru.nikkitavr.geotagger.users_service.controller;

import org.springframework.web.bind.annotation.*;
import ru.nikkitavr.geotagger.users_service.dto.SentInviteRequestDto;
import ru.nikkitavr.geotagger.users_service.dto.SentInviteResponseDto;
import ru.nikkitavr.geotagger.users_service.service.InviteService;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}/invites-sent")
public class SentInvitesController {

    private final InviteService inviteService;


    public SentInvitesController(InviteService inviteService) {
        this.inviteService = inviteService;
    }

    @PostMapping
    public void sendInvite(@PathVariable long userId, @RequestBody SentInviteRequestDto sentInvitesDto){
        inviteService.sendInvite(userId, sentInvitesDto);
    }

    @GetMapping
    public List<SentInviteResponseDto> getAllSentInvites(@PathVariable long userId){
        return inviteService.getAllOwnedInvites(userId);
    }

    @DeleteMapping("/{inviteId}")
    public void deleteSentInvite(@PathVariable long userId, @PathVariable long inviteId){
        inviteService.deleteOwnedInvite(userId, inviteId);
    }
}
