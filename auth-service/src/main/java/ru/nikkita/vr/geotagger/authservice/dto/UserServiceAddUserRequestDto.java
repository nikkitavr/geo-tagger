package ru.nikkita.vr.geotagger.authservice.dto;

import lombok.Data;

@Data
public class UserServiceAddUserRequestDto {
    private String login;
    private String email;
    private String phoneNumber;
}
