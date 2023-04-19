package ru.nikkita.vr.geotagger.authservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import ru.nikkita.vr.geotagger.authservice.dto.UserLoginRequestDto;
import ru.nikkita.vr.geotagger.authservice.dto.UserRegistrationRequestDto;
import ru.nikkita.vr.geotagger.authservice.model.User;
import ru.nikkita.vr.geotagger.authservice.security.JwtUtil;
import ru.nikkita.vr.geotagger.authservice.service.RegistrationService;

import javax.management.InstanceAlreadyExistsException;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final RegistrationService registrationService;
    private final JwtUtil jwtUtil;

    private final AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public Map<String, String> performLogin(
            @RequestBody UserLoginRequestDto userLoginRequestDto
    ) {
        UsernamePasswordAuthenticationToken authInputToken =
                new UsernamePasswordAuthenticationToken(userLoginRequestDto.getLogin(),
                        userLoginRequestDto.getPassword());

        authenticationManager.authenticate(authInputToken);
        User user = registrationService.getUserByLogin(userLoginRequestDto.getLogin());
        return Map.of(
                "jwt",
                jwtUtil.generateToken(user.getId(), user.getRolesAsStringList())
        );
    }


    @PostMapping("/registration")
    public Map<String, String> performRegistration(
            @RequestBody UserRegistrationRequestDto userRegistrationRequestDto
    ) throws InstanceAlreadyExistsException {
        registrationService.register(userRegistrationRequestDto);
        User user = registrationService.getUserByLogin(userRegistrationRequestDto.getLogin());

        //TODO: add call to userService as Admin

        return Map.of(
                "jwt",
                jwtUtil.generateToken(user.getId(), user.getRolesAsStringList())
        );
    }

//    @GetMapping()
//    public String getGodToken(){
//        return jwtUtil.generateGodToken();
//    }

}
