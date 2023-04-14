package ru.nikkitavr.geotagger.geoservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bouncycastle.asn1.cmp.GenRepContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import ru.nikkitavr.geotagger.geoservice.handler.GeoSharingHandler;

@Configuration
@EnableWebSocket
public class GeoSharingConfig implements WebSocketConfigurer {

    //Либо внедрить сам бин (компонент) через @Lazy
    @Autowired
    private ApplicationContext context;

    @Bean
    public WebSocketHandler geoSharingHandlerComponent() {
        return context.getBean(GeoSharingHandler.class);
    }
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.
                addHandler(
                        geoSharingHandlerComponent(),
                        "/geo-share").
                setAllowedOrigins("*");
    }

//    @Bean
//    public WebSocketHandler myHandler(){
//        return new GeoSharingHandler(objectMapper());
//    }

//    @Bean
//    public ObjectMapper objectMapper(){
//        return new ObjectMapper();
//    }
}
