package ru.nikkitavr.geotagger.users_service.controller;

import jakarta.security.auth.message.AuthException;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.nikkitavr.geotagger.users_service.dto.UserResponseDto;
import ru.nikkitavr.geotagger.users_service.security.AuthUser;
import ru.nikkitavr.geotagger.users_service.security.UserDetailsImpl;
import ru.nikkitavr.geotagger.users_service.service.UserService;

@RestController
@RequestMapping("/me")
@AllArgsConstructor
public class UsersController {
    private final UserService userService;

    @GetMapping
    public UserResponseDto getUser() throws AuthException {
        return userService.getUserById(AuthUser.getId());
    }

//    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
//    @ResponseStatus(HttpStatus.CREATED)
//    public BookResponseDto addUser(@Valid @ModelAttribute BookRequestDto bookRequestDto) {
//        return bookService.createBook(bookRequestDto);
//    }
}
