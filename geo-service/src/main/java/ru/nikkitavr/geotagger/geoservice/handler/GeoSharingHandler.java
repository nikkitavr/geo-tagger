package ru.nikkitavr.geotagger.geoservice.handler;

import org.springframework.boot.web.servlet.server.Session;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Random;

public class GeoSharingHandler extends TextWebSocketHandler {

    String income;
    private Thread workerThread;

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException, InterruptedException {
        income = message.getPayload();
        // Ваша логика обработки сообщения здесь
        if (income.equals("random")) {
            if (workerThread == null) {
                workerThread = new Thread(() -> {
                    while (!Thread.interrupted()) {
                        try {
                            Thread.sleep(1000);
                            session.sendMessage(new TextMessage(String.valueOf(new Random().nextInt(0, 10))));
                        } catch (InterruptedException | IOException e) {
                            break;
                        }
                    }
                });
                workerThread.start();
            }
        } else if (income.equals("stop")) {
            if (workerThread != null) {
                workerThread.interrupt();
                workerThread = null;
            }
        } else {
            session.sendMessage(new TextMessage("Unknown command: " + income));
        }
        //session.sendMessage(new TextMessage("Hello, " + income + "!"));
    }
}