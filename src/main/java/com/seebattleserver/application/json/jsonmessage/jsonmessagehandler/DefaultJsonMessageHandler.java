package com.seebattleserver.application.json.jsonmessage.jsonmessagehandler;

import com.google.gson.Gson;
import com.seebattleserver.application.json.jsonmessage.JsonMessage;
import org.springframework.web.socket.TextMessage;

public class DefaultJsonMessageHandler implements JsonMessageHandler {
    private Gson gson;

    public DefaultJsonMessageHandler() {
        gson = new Gson();
    }

    @Override
    public String handle(TextMessage textMessage) {
        JsonMessage jsonMessage = gson.fromJson(textMessage.getPayload(), JsonMessage.class);
        String messageStr = jsonMessage.getContent().trim();
        return messageStr;
    }
}
