package ru.nikkita.vr.geotagger.authservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.nikkita.vr.geotagger.authservice.dto.UserServiceAddUserRequestDto;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RemoteCallService {

    private final DiscoveryClient discoveryClient;
    private final ObjectMapper objectMapper;
    //private final RestTemplate restTemplate;

    @Value("${spring.cloud.gateway.name}")
    private String cloudGatewayName;
    @Value("${security.jwt.god-token}")
    private String JWT;


    public String addUserToUserService(UserServiceAddUserRequestDto dto) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(JWT);
        headers.setContentType(MediaType.APPLICATION_JSON);

        String body = objectMapper.writeValueAsString(dto);

        String url = getGatewayHost() + "/users-service/api/inner/users";
        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    new HttpEntity<>(body, headers),
                    String.class);
            return response.getBody();
        } catch (Exception e){
            e.printStackTrace();
            return e.toString();
        }
    }

    private String getGatewayHost(){
        List<ServiceInstance> instances = discoveryClient.getInstances(cloudGatewayName);
        if (instances.isEmpty()) {
            throw new RuntimeException("No instances available for service: " + cloudGatewayName);
        }
        return instances.get(0).getUri().toString();
    }
}
