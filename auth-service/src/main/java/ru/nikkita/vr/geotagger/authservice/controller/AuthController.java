package ru.nikkita.vr.geotagger.authservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.nikkita.vr.geotagger.authservice.dto.UserLoginRequestDto;
import ru.nikkita.vr.geotagger.authservice.dto.UserRegistrationRequestDto;
import ru.nikkita.vr.geotagger.authservice.mapper.UserMapper;
import ru.nikkita.vr.geotagger.authservice.model.User;
import ru.nikkita.vr.geotagger.authservice.security.JwtUtil;
import ru.nikkita.vr.geotagger.authservice.service.RegistrationService;
import ru.nikkita.vr.geotagger.authservice.service.RemoteCallService;

import javax.management.InstanceAlreadyExistsException;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final RegistrationService registrationService;
    private final JwtUtil jwtUtil;
    private final RemoteCallService remoteCallService;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;

    @PostMapping("/login")
    public Map<String, String> performLogin(
            @RequestBody UserLoginRequestDto userLoginRequestDto
    ) {
        UsernamePasswordAuthenticationToken authInputToken =
                new UsernamePasswordAuthenticationToken(userLoginRequestDto.getLogin(),
                        userLoginRequestDto.getPassword());

        Authentication authentication = authenticationManager.authenticate(authInputToken);
        User user = registrationService.getUserByLogin(userLoginRequestDto.getLogin());
        authentication.getPrincipal();
        return Map.of(
                "jwt",
                jwtUtil.generateToken(user.getLogin(), user.getRolesAsStringList())
        );
    }


    @PostMapping("/registration")
    public Map<String, String> performRegistration(
            @RequestBody UserRegistrationRequestDto userRegistrationRequestDto
    ) throws InstanceAlreadyExistsException, JsonProcessingException {
        registrationService.register(userRegistrationRequestDto);
        User user = registrationService.getUserByLogin(userRegistrationRequestDto.getLogin());

        String response = remoteCallService.addUserToUserService(userMapper.toUserServiceRequestDto(user));
        System.out.println(response);

        return Map.of(
                "jwt",
                jwtUtil.generateToken(user.getLogin(), user.getRolesAsStringList())
        );
    }

    @GetMapping("/god")
    public String godToken(){
        return jwtUtil.generateGodToken();
    }
}
