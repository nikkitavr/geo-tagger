package ru.nikkitavr.geotagger.users_service.model;

import jakarta.persistence.*;
import jakarta.ws.rs.NotFoundException;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;
import org.hibernate.annotations.WhereJoinTable;
import org.springframework.context.annotation.Configuration;
import ru.nikkitavr.geotagger.users_service.dto.UserRequestDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Table(name = "users")
@Entity
@Data
@NoArgsConstructor
public class User extends BaseEntity {
    String login;
    String email;
    @Column(name = "phone_number")
    String phoneNumber;

    @OneToMany(mappedBy = "user")
    List<Relationship> ownedRelationships;
    @OneToMany(mappedBy = "friend")
    List<Relationship> relatedToRelationships;

    public List<User> getFriends(){
        List<User> allFriends = new ArrayList<>();

        for (Relationship relationship:
             ownedRelationships) {
            if(relationship.getStatus() == RelationshipStatus.FRIEND){
                allFriends.add(relationship.getFriend());
            }
        }
        for (Relationship relationship:
                relatedToRelationships) {
            if(relationship.getStatus() == RelationshipStatus.FRIEND){
                allFriends.add(relationship.getUser());
            }
        }
        return allFriends;
    }

    public List<User> getInivteRecipients(){
        List<User> recipients = new ArrayList<>();

        for (Relationship relationship:
                ownedRelationships) {
            if(relationship.getStatus() == RelationshipStatus.INVITED){
                recipients.add(relationship.getFriend());
            }
        }

        return recipients;
    }

    public List<User> getInivteSenders(){
        List<User> recipients = new ArrayList<>();

        for (Relationship relationship:
                relatedToRelationships) {
            if(relationship.getStatus() == RelationshipStatus.INVITED){
                recipients.add(relationship.getUser());
            }
        }

        return recipients;
    }

    public List<Relationship> getAllRelationships(){
        List<Relationship> allRelationships = new ArrayList<>(ownedRelationships.size() + relatedToRelationships.size());
        allRelationships.addAll(ownedRelationships);
        allRelationships.addAll(relatedToRelationships);
        return allRelationships;
    }

    public User(UserRequestDto userRequestDto){
        setLogin(userRequestDto.getLogin());
        setEmail(userRequestDto.getEmail());
        setPhoneNumber(userRequestDto.getPhoneNumber());
    }
}
