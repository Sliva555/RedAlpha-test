package com.redalpha.test.call.api.domain;


public class Message {

    public Message(String message) {
        this.message = message;
    }

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
