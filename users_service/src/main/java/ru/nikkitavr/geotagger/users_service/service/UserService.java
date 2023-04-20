package ru.nikkitavr.geotagger.users_service.service;

import jakarta.ws.rs.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nikkitavr.geotagger.users_service.dto.UserRequestDto;
import ru.nikkitavr.geotagger.users_service.dto.UserResponseDto;
import ru.nikkitavr.geotagger.users_service.mapper.UserMapper;
import ru.nikkitavr.geotagger.users_service.model.User;
import ru.nikkitavr.geotagger.users_service.repository.UserRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<UserResponseDto> getAllUsers(){
        return userMapper.toUserDto(userRepository.findAll());
    }

    public UserResponseDto getUserById(long id){

        //TODO: Исправить маппер, не маппит
        return userMapper.toUserDto(getUser(id));
    }

    public User getUserByUsername(String username){
        return userRepository.findByLogin(username)
                .orElseThrow(() -> new NotFoundException("Not found book by username = " + username));
    }

    @Transactional
    public void createUser(UserRequestDto userRequestDto){
        userRepository.save(new User(userRequestDto));
    }

    private User getUser(long id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not found user by id = " + id));
        System.out.println(user);
        return user;
    }
}
