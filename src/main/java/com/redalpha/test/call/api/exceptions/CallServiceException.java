package com.redalpha.test.call.api.exceptions;

/**
 * Created by alevkovsky on 1/27/2017.
 */
public class CallServiceException extends RuntimeException{

    public CallServiceException(String message) {
        super(message);
    }

    @SuppressWarnings("unused")
    public CallServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
