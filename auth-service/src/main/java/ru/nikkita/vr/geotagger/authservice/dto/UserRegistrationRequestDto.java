package ru.nikkita.vr.geotagger.authservice.dto;

import lombok.Data;

@Data
public class UserRegistrationRequestDto {
    private String login;
    private String password;
    private String phoneNumber;
    private String email;
}
