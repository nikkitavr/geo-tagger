package ru.nikkitavr.geotagger.geoservice.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
public class ServiceConstants {
    @Value("${redis.geo.usersGeoSet}")
    public String geoSet;
    @Value("${websocket.messages.latency-ms}")
    public int threadLatency;
}
