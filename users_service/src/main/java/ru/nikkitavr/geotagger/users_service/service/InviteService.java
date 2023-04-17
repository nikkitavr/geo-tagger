package ru.nikkitavr.geotagger.users_service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.persistence.EntityManager;
import jakarta.ws.rs.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nikkitavr.geotagger.users_service.dto.ReceivedInviteDto;
import ru.nikkitavr.geotagger.users_service.dto.SentInviteRequestDto;
import ru.nikkitavr.geotagger.users_service.dto.SentInviteResponseDto;
import ru.nikkitavr.geotagger.users_service.mapper.UserMapper;
import ru.nikkitavr.geotagger.users_service.model.Relationship;
import ru.nikkitavr.geotagger.users_service.model.RelationshipStatus;
import ru.nikkitavr.geotagger.users_service.model.User;
import ru.nikkitavr.geotagger.users_service.repository.RelationshipsRepository;
import ru.nikkitavr.geotagger.users_service.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class InviteService {
    private final UserRepository userRepository;
    private final RelationshipsRepository relationshipsRepository;


    public void sendInvite(long userId, SentInviteRequestDto sentInviteRequestDto){

        User targetUser = userRepository.findByLogin(sentInviteRequestDto.getLogin())
                .orElseThrow(NotFoundException::new);
        User ownerUser = userRepository.findById(userId)
                .orElseThrow(NotFoundException::new);
        Relationship relationship = new Relationship(ownerUser, targetUser, RelationshipStatus.INVITED);
        relationshipsRepository.save(relationship);
    }

    public List<SentInviteResponseDto> getAllOwnedInvites(long userId){
       User owner  = userRepository.findById(userId).orElseThrow(NotFoundException::new);
       List<Relationship> ownedRelationships = owner.getOwnedRelationships();
       List<SentInviteResponseDto> inviteDtos = new ArrayList<>();
        for (Relationship relationship:
             ownedRelationships) {
            if(relationship.getStatus() == RelationshipStatus.INVITED) {
                inviteDtos.add(
                        new SentInviteResponseDto(
                                relationship.getId(),
                                relationship.getFriend()));
            }
        }
        return inviteDtos;
    }

    public void deleteOwnedInvite(long userId, long inviteId){
        User user = userRepository.findById(userId)
                .orElseThrow(NotFoundException::new);
        List<Relationship> ownedRelationships = user.getOwnedRelationships();
        for (Relationship relationship:
             ownedRelationships) {
            if(relationship.getStatus() == RelationshipStatus.INVITED
                    && relationship.getId() == inviteId){
                relationshipsRepository.deleteById(inviteId);
                return;
            }
        }
        throw new NotFoundException();
    }


    public List<ReceivedInviteDto> getAllReceivedInvites(long userId){
        User owner  = userRepository.findById(userId).orElseThrow(NotFoundException::new);
        List<Relationship> relatedRelationships = owner.getRelatedToRelationships();
        List<ReceivedInviteDto> inviteDtos = new ArrayList<>();
        for (Relationship relationship:
                relatedRelationships) {
            if(relationship.getStatus() == RelationshipStatus.INVITED) {
                inviteDtos.add(
                        new ReceivedInviteDto(
                                relationship.getId(),
                                relationship.getUser()));
            }
        }
        return inviteDtos;
    }

    @Transactional
    public void acceptReceivedInvite(long userId, long inviteId) throws JsonProcessingException {
        Relationship relationship =  getRelatedInvite(userId, inviteId);
        relationship.setStatus(RelationshipStatus.FRIEND);
        long friendId = relationship.getUser().getId();
    }

    @Transactional
    public void declineReceivedInvite(long userId, long inviteId){
        getRelatedInvite(userId, inviteId).setStatus(RelationshipStatus.DECLINED);
    }


    private Relationship getRelatedInvite(long userId, long inviteId){
        User user = userRepository.findById(userId)
                .orElseThrow(NotFoundException::new);
        List<Relationship> relatedRelationships = user.getRelatedToRelationships();

        return relatedRelationships
                .stream()
                .filter(r -> r.getId() == inviteId && r.getStatus() == RelationshipStatus.INVITED)
                .findFirst()
                .orElseThrow(NotFoundException::new);
    }

    public long getInviteOwnerId(long inviteId){
        Relationship relationship =
                relationshipsRepository
                        .findById(inviteId)
                        .orElseThrow(NotFoundException::new);

        return relationship.getUser().getId();
    }
}
