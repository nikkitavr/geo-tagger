package ru.nikkitavr.geotagger.users_service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.nikkitavr.geotagger.users_service.model.User;

@Data
@NoArgsConstructor
public class ReceivedInviteDto {
    private long id;

    //UserDetails но сейчас просто логин
    private String senderLogin;

    public ReceivedInviteDto(long id, User sender){
        this.id = id;
        this.senderLogin = sender.getLogin();
    }

}
