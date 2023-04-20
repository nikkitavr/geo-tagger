package ru.nikkitavr.geotagger.users_service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.nikkitavr.geotagger.users_service.model.User;

@Data
@NoArgsConstructor
public class SentInviteResponseDto {
    private long id;
    private String recipientLogin;

    public SentInviteResponseDto(long id, User recipient){
        this.id = id;
        this.recipientLogin = recipient.getLogin();
    }

}
