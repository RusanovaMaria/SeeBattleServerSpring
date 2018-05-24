package com.seebattleserver.service.message;

public class Message {

    private String context;

    public Message() {

    }

    public Message(String message) {
        this.context = message;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

}

