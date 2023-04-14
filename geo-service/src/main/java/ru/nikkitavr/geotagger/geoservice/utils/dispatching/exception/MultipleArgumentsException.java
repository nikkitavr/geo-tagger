package ru.nikkitavr.geotagger.geoservice.utils.dispatching.exception;

import ru.nikkitavr.geotagger.geoservice.utils.dispatching.MethodType;

public class MultipleArgumentsException extends RuntimeException {
    private static final String ErrorCode = "Method, annotated with " + MethodType.class.getName() + " can have only one Argument";

    public MultipleArgumentsException() {
        super(ErrorCode);
    }
}
