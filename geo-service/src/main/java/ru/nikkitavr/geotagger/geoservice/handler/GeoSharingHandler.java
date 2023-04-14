package ru.nikkitavr.geotagger.geoservice.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import ru.nikkitavr.geotagger.geoservice.dto.ClientCommandMessageDto;
import ru.nikkitavr.geotagger.geoservice.dto.ClientDataMessageDto;
import ru.nikkitavr.geotagger.geoservice.service.GeoSharingService;
import ru.nikkitavr.geotagger.geoservice.utils.Method;
import ru.nikkitavr.geotagger.geoservice.utils.dispatching.MessageBaseEntity;
import ru.nikkitavr.geotagger.geoservice.utils.dispatching.MessageDispatcher;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

@Component
public class GeoSharingHandler extends TextWebSocketHandler {

//    String income;
//    private Thread workerThread;
    ObjectMapper objectMapper;
    MessageDispatcher messageDispatcher;

    @Autowired
    public GeoSharingHandler(ObjectMapper objectMapper,
                             GeoSharingService geoSharingService,
                             MessageDispatcher messageDispatcher) {
        this.objectMapper = objectMapper;
        this.messageDispatcher = messageDispatcher;
        this.messageDispatcher.registerServiceClass(geoSharingService);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException, InvocationTargetException, IllegalAccessException {
        messageDispatcher.routeMessage(getJson(message), session);
        //handleMessage(session, message);
    }

    private MessageBaseEntity getJson(TextMessage message) throws JsonProcessingException {
        JsonNode jsonNode = objectMapper.readTree(message.getPayload());
        Method method = Method.getCommandFromString(jsonNode.get("method").asText());

        if(method == Method.POST){
            ClientDataMessageDto dto = objectMapper.readValue(message.getPayload(), ClientDataMessageDto.class);
            System.out.println(dto);
            return dto;
        }
        return  objectMapper.readValue(message.getPayload(), ClientCommandMessageDto.class);
    }
}
//        income = message.getPayload();
//        // Ваша логика обработки сообщения здесь
//        if (income.equals("random")) {
//            if (workerThread == null) {
//                workerThread = new Thread(() -> {
//                    while (!Thread.interrupted()) {
//                        try {
//                            Thread.sleep(1000);
//                            session.sendMessage(new TextMessage(String.valueOf(new Random().nextInt(0, 10))));
//                        } catch (InterruptedException | IOException e) {
//                            break;
//                        }
//                    }
//                });
//                workerThread.start();
//            }
//        } else if (income.equals("stop")) {
//            if (workerThread != null) {
//                workerThread.interrupt();
//                workerThread = null;
//            }
//        } else {
//            session.sendMessage(new TextMessage("Unknown command: " + income));
//        }
//session.sendMessage(new TextMessage("Hello, " + income + "!"));