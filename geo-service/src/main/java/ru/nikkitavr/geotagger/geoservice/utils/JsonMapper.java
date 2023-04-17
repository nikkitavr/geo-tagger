package ru.nikkitavr.geotagger.geoservice.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import ru.nikkitavr.geotagger.geoservice.dto.ClientCommandMessageDto;
import ru.nikkitavr.geotagger.geoservice.dto.ClientDataMessageDto;
import ru.nikkitavr.geotagger.geoservice.utils.dispatching.MessageBaseEntity;

@Component
public class JsonMapper {

    ObjectMapper objectMapper;

    public JsonMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);

    }

    public MessageBaseEntity getJson(TextMessage message) throws JsonProcessingException {
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
