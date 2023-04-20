package ru.nikkitavr.geotagger.users_service.security;

import jakarta.security.auth.message.AuthException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthUser {
    public static long getId() throws AuthException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth == null || !auth.isAuthenticated()){
            throw new AuthException("dffd");
        }
        return  ((UserDetailsImpl)auth.getPrincipal()).getId();
    }
}
