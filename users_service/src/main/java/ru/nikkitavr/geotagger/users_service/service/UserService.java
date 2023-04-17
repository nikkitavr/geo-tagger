package ru.nikkitavr.geotagger.users_service.service;

import jakarta.ws.rs.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.nikkitavr.geotagger.users_service.dto.UserRequestDto;
import ru.nikkitavr.geotagger.users_service.dto.UserResponseDto;
import ru.nikkitavr.geotagger.users_service.mapper.UserMapper;
import ru.nikkitavr.geotagger.users_service.model.User;
import ru.nikkitavr.geotagger.users_service.repository.UserRepository;

import java.util.List;
import java.util.Set;

@Service
public class UserService {
    @Autowired
    public UserService(
            UserRepository userRepository,
            UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<UserResponseDto> getAllUsers(){
        return userMapper.toUserDto(userRepository.findAll());
    }

    public UserResponseDto getUserById(long id){

        return userMapper.toUserDto(getUser(id));
    }

    public UserResponseDto createUserAndGet(UserRequestDto userRequestDto){

        return userMapper.toUserDto(
                userRepository.save(
                        new User(userRequestDto)
                )
        );
    }

    private User getUser(long id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not found book by id = " + id));
        System.out.println(user);
        return user;
    }
}
