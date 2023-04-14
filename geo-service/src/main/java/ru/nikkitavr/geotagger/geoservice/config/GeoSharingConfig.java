package ru.nikkitavr.geotagger.geoservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import ru.nikkitavr.geotagger.geoservice.handler.GeoSharingHandler;

@Configuration
@EnableWebSocket
public class GeoSharingConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(myHandler(), "/geo-share").setAllowedOrigins("*");
    }

    @Bean
    public WebSocketHandler myHandler(){
        return new GeoSharingHandler();
    }
}
