package ru.nikkitavr.geotagger.geoservice.utils.dispatching.exception;

public class MethodNotFoundException extends RuntimeException{
    private static final String ErrorCode = "Cannot find method with provided annotated type: ";

    public MethodNotFoundException(String methodType) {
        super(ErrorCode + methodType);
    }
}
