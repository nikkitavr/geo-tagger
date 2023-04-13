package ru.nikkitavr.geotagger.geoteurekaserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class GeotEurekaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(GeotEurekaServerApplication.class, args);
	}

}
