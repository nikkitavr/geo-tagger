package ru.nikkitavr.geotagger.users_service.dto;

import ru.nikkitavr.geotagger.users_service.model.User;

public class SentInviteRequestDto {
    private String recipientLogin;

    public String getLogin() {
        return recipientLogin;
    }

    public String getRecipientLogin() {
        return recipientLogin;
    }

    public void setRecipientLogin(String recipientLogin) {
        this.recipientLogin = recipientLogin;
    }
}

