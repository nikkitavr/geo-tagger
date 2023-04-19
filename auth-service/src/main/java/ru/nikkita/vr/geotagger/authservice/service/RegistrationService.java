package ru.nikkita.vr.geotagger.authservice.service;

import jakarta.persistence.EntityManager;
import jakarta.ws.rs.NotFoundException;
import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nikkita.vr.geotagger.authservice.Utils.Roles;
import ru.nikkita.vr.geotagger.authservice.dto.UserRegistrationRequestDto;
import ru.nikkita.vr.geotagger.authservice.mapper.UserMapper;
import ru.nikkita.vr.geotagger.authservice.model.Role;
import ru.nikkita.vr.geotagger.authservice.model.User;
import ru.nikkita.vr.geotagger.authservice.repository.RoleRepository;
import ru.nikkita.vr.geotagger.authservice.repository.UserRepository;

import javax.management.InstanceAlreadyExistsException;
import java.util.Collections;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RegistrationService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final EntityManager entityManager;

    @Transactional
    public void register(UserRegistrationRequestDto userRegistrationRequestDto) throws InstanceAlreadyExistsException {
        if(isUserWithLoginExists(userRegistrationRequestDto.getLogin())){
            throw new InstanceAlreadyExistsException("User with this login already exist");
        }

//        User user = new User(
//                userRegistrationRequestDto,
//                roleRepository.getRoleByTitle(Roles.ROLE_USER.name())
//                        .orElseThrow(NotFoundException::new),
//                passwordEncoder.encode(
//                        userRegistrationRequestDto.getPassword()
//                )
//        );

        Optional<Role> role = roleRepository.getRoleByTitle(Roles.ROLE_USER.name());
        User user = new User();
        user.setPassword(passwordEncoder.encode(userRegistrationRequestDto.getPassword()));
        user.setRoles(Collections.singletonList(role.get()));
        //user.getRoles().add(role.get());
        user.setLogin(userRegistrationRequestDto.getLogin());
        user.setEmail(userRegistrationRequestDto.getEmail());
        user.setPhoneNumber(userRegistrationRequestDto.getPhoneNumber());

        if (role.get().getUsers() == null){
            role.get().setUsers(Collections.singletonList(user));
        }
        userRepository.save(user);
        roleRepository.save(role.get());
    }

    public User getUserByLogin(String login){
        entityManager.clear();
        return userRepository.findByLogin(login)
                .orElseThrow(NotFoundException::new);
    }

    private boolean isUserWithLoginExists(String login){
        Optional<User> user = userRepository.findByLogin(login);
        if(user.isEmpty()){
            return false;
        }
        return true;
    }
}
