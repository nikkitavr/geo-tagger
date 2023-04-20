package ru.nikkitavr.geotagger.users_service.dto;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class UserResponseDto {
    private Long id;
    private String login;
    private String email;
    private String phoneNumber;
}




