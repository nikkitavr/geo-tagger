package ru.nikkitavr.geotagger.users_service.controller.Inner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.nikkitavr.geotagger.users_service.dto.UserRequestDto;
import ru.nikkitavr.geotagger.users_service.dto.UserResponseDto;
import ru.nikkitavr.geotagger.users_service.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/inner/users")
public class InnerApiUsersController {

    @Autowired
    public InnerApiUsersController(UserService userService) {
        this.userService = userService;
    }

    private final UserService userService;

    @PostMapping
    public UserResponseDto addUser(@RequestBody UserRequestDto userRequestDto){
        return userService.createUserAndGet(userRequestDto);
    }

    @GetMapping
    public List<UserResponseDto> getAllUsers(){
        return userService.getAllUsers();
    }
}
