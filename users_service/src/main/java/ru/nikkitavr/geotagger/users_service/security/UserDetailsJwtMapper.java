package ru.nikkitavr.geotagger.users_service.security;


import com.auth0.jwt.interfaces.Claim;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.nikkitavr.geotagger.users_service.service.UserService;

import java.util.Map;

@AllArgsConstructor
@Component
public class UserDetailsJwtMapper {

    private final UserService userService;

    public UserDetailsImpl claimsToUserDetails(Map<String, Claim> claims){
        String username = claims.get("username").asString();

        return new UserDetailsImpl(
                userService.getUserByUsername(username).getId(),
                username,
                claims.get("roles").asList(String.class)
        );
    }
}
