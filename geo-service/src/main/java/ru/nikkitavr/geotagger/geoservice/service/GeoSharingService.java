package ru.nikkitavr.geotagger.geoservice.service;


import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import ru.nikkitavr.geotagger.geoservice.dto.ClientCommandMessageDto;
import ru.nikkitavr.geotagger.geoservice.dto.ClientDataMessageDto;
import ru.nikkitavr.geotagger.geoservice.utils.Method;
import ru.nikkitavr.geotagger.geoservice.utils.dispatching.MessageBaseEntity;
import ru.nikkitavr.geotagger.geoservice.utils.dispatching.MethodType;

import java.io.IOException;

@Component
public class GeoSharingService {


    @MethodType("start")
    public void startGeoStreaming(ClientCommandMessageDto messageDto, WebSocketSession session) throws IOException {
        session.sendMessage(new TextMessage("message handled by 'start' method"));
    }

    @MethodType("post")
    public void putGeo(ClientDataMessageDto messageDto, WebSocketSession session) throws IOException {
        session.sendMessage(new TextMessage("message handled by 'post' method"));
        session.sendMessage(new TextMessage("data: " + messageDto.getData()));
    }


}
