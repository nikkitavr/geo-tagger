package ru.nikkitavr.geotagger.geoservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class GeoServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(GeoServiceApplication.class, args);
    }

}
