package ru.nikkitavr.geotagger.geoservice.utils.dispatching.exception;

import ru.nikkitavr.geotagger.geoservice.utils.dispatching.MethodType;

public class MultipleMethodTypeUsageException extends RuntimeException{
    private static final String ErrorCode = "You can use only one annotation " + MethodType.class.getName() + " per method";

    public MultipleMethodTypeUsageException() {
        super(ErrorCode);
    }
}
