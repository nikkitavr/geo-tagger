package ru.nikkitavr.geotagger.users_service.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="relationships")
@Data
@NoArgsConstructor
public class Relationship extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "friend_id")
    private User friend;
    @Enumerated(EnumType.STRING)
    private RelationshipStatus status;

    public Relationship(User user, User friend, RelationshipStatus status) {
        this.user = user;
        this.friend = friend;
        this.status = status;
    }

//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }
//
//    public User getFriend() {
//        return friend;
//    }
//
//    public void setFriend(User friend) {
//        this.friend= friend;
//    }
//
//    public RelationshipStatus getStatus() {
//        return status;
//    }
//
//    public void setStatus(RelationshipStatus status) {
//        this.status = status;
//    }
}
