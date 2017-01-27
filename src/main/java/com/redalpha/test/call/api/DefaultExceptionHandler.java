package com.redalpha.test.call.api;

import com.redalpha.test.call.api.domain.Message;
import com.redalpha.test.call.api.exceptions.CallValidatorException;
import lombok.extern.log4j.Log4j;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alevkovsky on 1/26/2017.
 */
@Log4j
@RestControllerAdvice
public class DefaultExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity exceptionHandler(Exception ex) {
        log.error(ex.getMessage(), ex);
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(CallValidatorException.class)
    public ResponseEntity exceptionHandler(CallValidatorException ex) {
        log.error(ex.getMessage(), ex);
        return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity exceptionHandler(MethodArgumentNotValidException ex) {
        List<String> messages = new ArrayList<>(ex.getBindingResult().getErrorCount());
        ex.getBindingResult().getFieldErrors().forEach(item -> messages.add(generateMessage(item)));
        return new ResponseEntity(new Message(messages.toString()), HttpStatus.BAD_REQUEST);
    }

    private String generateMessage(FieldError fieldError) {
        return "You enter: " + fieldError.getRejectedValue() + ". " + fieldError.getDefaultMessage();
    }
}
