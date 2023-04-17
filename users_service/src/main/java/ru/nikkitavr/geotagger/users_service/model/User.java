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
    String name;
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

//    public List<Relationship> getOwnedRelationships() {
//        return ownedRelationships;
//    }
//
//    public void setOwnedRelationships(List<Relationship> ownedRelationships) {
//        this.ownedRelationships = ownedRelationships;
//    }
//
//    public List<Relationship> getRelatedToRelationships() {
//        return relatedToRelationships;
//    }
//
//    public void setRelatedToRelationships(List<Relationship> relatedToRelationships) {
//        this.relatedToRelationships = relatedToRelationships;
//    }
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getLogin() {
//        return login;
//    }
//
//    public void setLogin(String login) {
//        this.login = login;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getPhoneNumber() {
//        return phoneNumber;
//    }
//
//    public void setPhoneNumber(String phoneNumber) {
//        this.phoneNumber = phoneNumber;
//    }


    public User(UserRequestDto userRequestDto){
        setName(userRequestDto.getName());
        setLogin(userRequestDto.getLogin());
        setEmail(userRequestDto.getEmail());
        setPhoneNumber(userRequestDto.getPhoneNumber());
    }
}
