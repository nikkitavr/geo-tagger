package ru.nikkitavr.geotagger.users_service.dto;

import ru.nikkitavr.geotagger.users_service.model.User;

public class ReceivedInviteDto {
    private long id;

    //UserDetails но сейчас просто логин
    private String senderLogin;

    public ReceivedInviteDto(long id, User sender){
        this.id = id;
        this.senderLogin = sender.getLogin();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSenderLogin() {
        return senderLogin;
    }

    public void setSenderLogin(String senderLogin) {
        this.senderLogin = senderLogin;
    }
}
