package ru.nikkitavr.geotagger.geoservice.utils.dispatching.exception;

import ru.nikkitavr.geotagger.geoservice.utils.dispatching.MessageBaseEntity;
import ru.nikkitavr.geotagger.geoservice.utils.dispatching.MethodType;

public class InvalidArgumentException extends RuntimeException{
    private static final String ErrorCode = "Argument of method annotated with " + MethodType.class.getName() +
            " should be instance of type " + MessageBaseEntity.class.getName() +
            " or subtype of it. Provided argument type: ";

    public InvalidArgumentException(Class argument) {
        super(ErrorCode + argument);
    }
}
