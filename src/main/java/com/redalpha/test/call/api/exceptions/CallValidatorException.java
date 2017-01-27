package com.redalpha.test.call.api.exceptions;

/**
 * Created by alevkovsky on 1/26/2017.
 */
public class CallValidatorException extends RuntimeException {
    public CallValidatorException(String message) {
        super(message);
    }

    public CallValidatorException(String message, Throwable cause) {
        super(message, cause);
    }
}
