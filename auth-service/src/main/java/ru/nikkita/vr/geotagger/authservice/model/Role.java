package ru.nikkita.vr.geotagger.authservice.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Cascade;

import java.util.List;

@Data
@Entity
@Table(name = "roles")
public class Role extends BaseEntity {
    @Column(name = "role_title")
    private String title;
    @ManyToMany
    @JoinTable(
            schema = "public",
            name= "roles_users",
            joinColumns = {@JoinColumn(name = "role_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    private List<User> users;
}
