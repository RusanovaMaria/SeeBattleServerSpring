package com.seebattleserver.application.message;

import com.seebattleserver.application.user.User;


public class Message {

    private User from;
    private User to;
    private String message;

    public Message(String message){
        this.message = message;
    }

    public User getFrom() {
        return from;
    }

    public User getTo() {
        return to;
    }

    public String getMessage() {
        return message;
    }

}
