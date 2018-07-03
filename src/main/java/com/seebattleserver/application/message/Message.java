package com.seebattleserver.application.message;


import com.seebattleserver.application.user.User;

public class Message {
    private String content;
    private User to;

    public Message(String content){
        this.content = content;
    }

    public Message (String content, User to) {
        this.content = content;
        this.to = to;
    }

    public String getContent() {
        return content;
    }

    public User getTo() {
        return to;
    }
}
