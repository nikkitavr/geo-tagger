package ru.nikkita.vr.geotagger.authservice.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.nikkita.vr.geotagger.authservice.dto.UserRegistrationRequestDto;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "users")
@NoArgsConstructor
public class User extends BaseEntity {
    private String login;
    private String password;
    private String email;
    @Column(name = "phone_number")
    private String phoneNumber;
    @ManyToMany
    @JoinTable(
            schema = "public",
            name= "roles_users",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")}
    )
    private List<Role> roles;

    public User(UserRegistrationRequestDto dto, Role role, String password){
        this.login = dto.getLogin();
        this.password = password;
        this.email = dto.getEmail();
        this.phoneNumber = dto.getPhoneNumber();
        if(this.getRoles() == null){
            roles = new ArrayList<>();
        }
        this.getRoles().add(role);
    }

    public List<String> getRolesAsStringList(){
        List<String> rolesString = new ArrayList<>(roles.size());
        for (Role role:
             roles) {
            rolesString.add(role.getTitle());
        }
        return rolesString;
    }
}
