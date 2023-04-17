package ru.nikkitavr.geotagger.geoservice.controller;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import ru.nikkitavr.geotagger.geoservice.dto.ClientCommandMessageDto;
import ru.nikkitavr.geotagger.geoservice.dto.ClientDataMessageDto;
import ru.nikkitavr.geotagger.geoservice.service.UsersGeoService;
import ru.nikkitavr.geotagger.geoservice.utils.dispatching.MethodType;

import java.io.IOException;

@Component
@AllArgsConstructor
public class GeoSharingHandleController {

    UsersGeoService usersGeoService;
    @MethodType("start")
    public void startGeoStreaming(ClientCommandMessageDto messageDto, WebSocketSession session) throws IOException, InterruptedException {
        usersGeoService.startStreamFriendsGeo(messageDto.getUserId(), session);
    }

    @MethodType("post")
    public void putGeo(ClientDataMessageDto messageDto, WebSocketSession session) throws IOException {
        usersGeoService.saveGeo(messageDto);
    }

    @MethodType("ping")
    public void ping(ClientDataMessageDto messageDto, WebSocketSession session) throws IOException {
        session.sendMessage(new TextMessage("Pong"));
    }


}
