package ru.nikkitavr.geotagger.users_service.dto;

import ru.nikkitavr.geotagger.users_service.model.User;

public class SentInviteResponseDto {
    private long id;
    private String recipientLogin;

    public SentInviteResponseDto(long id, User recipient){
        this.id = id;
        this.recipientLogin = recipient.getLogin();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRecipientLogin() {
        return recipientLogin;
    }

    public void setRecipientLogin(String recipientLogin) {
        this.recipientLogin = recipientLogin;
    }
}
