package com.seebattleserver.application.json.jsonmessage;


public class JsonMessage {
    private String content;

    public JsonMessage() {

    }

    public JsonMessage(String content){
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
