package ru.nikkitavr.geotagger.users_service.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.nikkitavr.geotagger.users_service.security.JwtUtil;
import ru.nikkitavr.geotagger.users_service.security.UserDetailsImpl;
import ru.nikkitavr.geotagger.users_service.security.UserDetailsJwtMapper;

import java.io.IOException;

@AllArgsConstructor
@Component
public class JwtFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final UserDetailsJwtMapper userDetailsJwtMapper;
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            @NotNull HttpServletResponse response,
            @NotNull FilterChain filterChain
    ) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && !authHeader.isBlank() && authHeader.startsWith("Bearer ")) {
            String jwt = authHeader.substring(7);

            if (jwt.isBlank()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                        "Invalid JWT Token in Bearer Header");
            } else {
                try {
                    //Получаем UserDetails с помощью login в JWT
                    UserDetailsImpl userDetails =
                            userDetailsJwtMapper.claimsToUserDetails(
                                jwtUtil.validateTokenAndRetrieveClaim(jwt)
                            );
                    //Здесь мы помещаем userDetails в AuthContext, который потом может запросить в методе
                    //Этот UserDetails также будет использоваться для авторизации и прочего в SecurityConfig
                    PreAuthenticatedAuthenticationToken authToken =
                            new PreAuthenticatedAuthenticationToken(
                                    userDetails,
                                    jwt,
                                    userDetails.getAuthorities()
                            );

                    if (SecurityContextHolder.getContext().getAuthentication() == null) {
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }

                    //Т.е эта вещь скорее для авторизации. SpringSecurity (Config) работает
                    //с классом UserDetails. Видимо надо будет создать отдельный UserDetails
                    //который будет содержать чисто роль и id. Хз пока что
                } catch (JWTVerificationException exc) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                            "Invalid JWT Token");
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}
