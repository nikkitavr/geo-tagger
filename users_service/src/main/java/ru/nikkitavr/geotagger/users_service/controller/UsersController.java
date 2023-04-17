package ru.nikkitavr.geotagger.users_service.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.nikkitavr.geotagger.users_service.dto.UserResponseDto;
import ru.nikkitavr.geotagger.users_service.service.UserService;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UsersController {
    private final UserService userService;

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
