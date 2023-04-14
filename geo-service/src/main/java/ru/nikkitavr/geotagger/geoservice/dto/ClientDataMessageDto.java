package ru.nikkitavr.geotagger.geoservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.nikkitavr.geotagger.geoservice.utils.dispatching.MessageBaseEntity;


public class ClientDataMessageDto extends MessageBaseEntity {
    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
