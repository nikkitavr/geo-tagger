package ru.nikkitavr.geotagger.geoservice.handler;

import lombok.AllArgsConstructor;
import org.apache.catalina.User;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import ru.nikkitavr.geotagger.geoservice.controller.GeoSharingHandleController;
import ru.nikkitavr.geotagger.geoservice.service.UsersGeoService;
import ru.nikkitavr.geotagger.geoservice.utils.JsonMapper;
import ru.nikkitavr.geotagger.geoservice.utils.dispatching.MessageDispatcher;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

@Component

public class GeoSharingHandler extends TextWebSocketHandler {

//    String income;
//    private Thread workerThread;
    MessageDispatcher messageDispatcher;
    JsonMapper jsonMapper;

    public GeoSharingHandler(MessageDispatcher messageDispatcher, JsonMapper jsonMapper, GeoSharingHandleController geoSharingHandleController) {
        this.messageDispatcher = messageDispatcher;
        this.jsonMapper = jsonMapper;
        messageDispatcher.registerServiceClass(geoSharingHandleController);
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException, InvocationTargetException, IllegalAccessException {
        messageDispatcher.routeMessage(
                jsonMapper.getJson(message),
                session
        );
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