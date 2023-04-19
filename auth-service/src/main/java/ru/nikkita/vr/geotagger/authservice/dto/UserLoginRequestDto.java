package ru.nikkita.vr.geotagger.authservice.dto;

import lombok.Data;

@Data
public class UserLoginRequestDto {
    private String login;
    private String password;
}
