package com.seebattleserver.application.message;

import com.seebattleserver.application.user.User;


public class Message {

    private User from;
    private User to;
    private String message;
    private MessageType type;

   /* public Message(User to, String message) {
        this(null, to, message);
    }

    public Message(User from, User to, String message) {
        this.from = from;
        this.to = to;
        this.message = message;
        this.type = type;
    } */

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

    public MessageType getType() {
        return type;
    }

}
