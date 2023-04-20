package ru.nikkitavr.geotagger.users_service.dto;

import lombok.Data;
import ru.nikkitavr.geotagger.users_service.model.User;

@Data
public class SentInviteRequestDto {
    private String recipientLogin;

}

