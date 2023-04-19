package ru.nikkita.vr.geotagger.authservice.service;

import jakarta.ws.rs.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.nikkita.vr.geotagger.authservice.repository.UserRepository;
import ru.nikkita.vr.geotagger.authservice.security.UserDetailsImpl;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String login) {
        return new UserDetailsImpl(
                userRepository.findByLogin(login)
                        .orElseThrow(NotFoundException::new)
        );
    }

    public UserDetails loadUserById(long id){
        return new UserDetailsImpl(
                userRepository.findById(id)
                        .orElseThrow(NotFoundException::new)
        );
    }
}
