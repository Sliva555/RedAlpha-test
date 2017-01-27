package com.redalpha.test.call.api.exceptions;

/**
 * Created by alevkovsky on 1/26/2017.
 */
public class DaoException extends RuntimeException {
    public DaoException(String message) {
        super(message);
    }

    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }

    public DaoException(Throwable cause) {
        super(cause);
    }
}
