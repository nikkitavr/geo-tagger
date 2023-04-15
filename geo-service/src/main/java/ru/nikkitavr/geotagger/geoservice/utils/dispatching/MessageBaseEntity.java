package ru.nikkitavr.geotagger.geoservice.utils.dispatching;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class MessageBaseEntity {
    protected String method;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
