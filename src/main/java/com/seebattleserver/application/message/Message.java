package com.seebattleserver.application.message;

import com.seebattleserver.application.user.User;


public class Message {

    private User from;
    private User to;
    private String message;
    private MessageType type;

    public Message(User to, String message, MessageType type) {
        this(null, to, message, type);
    }

    public Message(User from, User to, String message, MessageType type) {
        this.from = from;
        this.to = to;
        this.message = message;
        this.type = type;
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
