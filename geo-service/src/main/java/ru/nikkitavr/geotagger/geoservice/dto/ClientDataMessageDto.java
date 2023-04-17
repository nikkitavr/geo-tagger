package ru.nikkitavr.geotagger.geoservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.geo.Point;
import ru.nikkitavr.geotagger.geoservice.utils.dispatching.MessageBaseEntity;

import java.util.Map;


@Data
public class ClientDataMessageDto extends MessageBaseEntity {
    Point data;

    long userId;

}
