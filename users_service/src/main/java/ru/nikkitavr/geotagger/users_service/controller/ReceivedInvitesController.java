package ru.nikkitavr.geotagger.users_service.controller;

import org.springframework.web.bind.annotation.*;
import ru.nikkitavr.geotagger.users_service.dto.ReceivedInviteDto;
import ru.nikkitavr.geotagger.users_service.service.InviteService;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}/invites-received")
public class ReceivedInvitesController {

    private final InviteService inviteService;


    public ReceivedInvitesController(InviteService inviteService) {
        this.inviteService = inviteService;
    }

    @GetMapping
    public List<ReceivedInviteDto> getAllReceivedInvites(@PathVariable long userId){
        return inviteService.getAllReceivedInvites(userId);
    }

    @PostMapping("/{inviteId}/accept")
    public void acceptReceivedInvite(@PathVariable long userId, @PathVariable long inviteId){
        inviteService.acceptReceivedInvite(userId, inviteId);
    }

    @PostMapping("/{inviteId}/decline")
    public void declineReceivedInvite(@PathVariable long userId, @PathVariable long inviteId){
        inviteService.declineReceivedInvite(userId, inviteId);
    }
}
