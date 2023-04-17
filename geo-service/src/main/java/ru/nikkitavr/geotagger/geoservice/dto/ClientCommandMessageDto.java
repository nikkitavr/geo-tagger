package ru.nikkitavr.geotagger.geoservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import ru.nikkitavr.geotagger.geoservice.utils.dispatching.MessageBaseEntity;

@Data
public class ClientCommandMessageDto extends MessageBaseEntity {
    long userId;
}
