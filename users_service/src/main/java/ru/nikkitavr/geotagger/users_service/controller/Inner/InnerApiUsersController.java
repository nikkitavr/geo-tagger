package ru.nikkitavr.geotagger.users_service.controller.Inner;

import lombok.AllArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import ru.nikkitavr.geotagger.users_service.dto.UserRequestDto;
import ru.nikkitavr.geotagger.users_service.dto.UserResponseDto;
import ru.nikkitavr.geotagger.users_service.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/inner/users")
@AllArgsConstructor
public class InnerApiUsersController {
    private final UserService userService;

    @PostMapping
    public void addUser(@RequestBody UserRequestDto userRequestDto){
        userService.createUser(userRequestDto);
    }

    @GetMapping
    public List<UserResponseDto> getAllUsers(){
        return userService.getAllUsers();
    }
}
