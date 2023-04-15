package ru.nikkitavr.geotagger.users_service.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.nikkitavr.geotagger.users_service.dto.UserRequestDto;
import ru.nikkitavr.geotagger.users_service.dto.UserResponseDto;
import ru.nikkitavr.geotagger.users_service.service.UserService;

@RestController
@RequestMapping("/users")
public class UsersController {

    private final UserService userService;

    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserResponseDto addUser(@RequestBody UserRequestDto userRequestDto){
        return userService.createUserAndGet(userRequestDto);
    }
    @GetMapping("/{id}")
    public UserResponseDto getUser(@PathVariable long id){
        return userService.getUserById(id);
    }

//    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
//    @ResponseStatus(HttpStatus.CREATED)
//    public BookResponseDto addUser(@Valid @ModelAttribute BookRequestDto bookRequestDto) {
//        return bookService.createBook(bookRequestDto);
//    }
}
