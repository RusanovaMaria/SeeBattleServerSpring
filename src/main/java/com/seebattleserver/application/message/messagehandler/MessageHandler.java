package com.seebattleserver.application.message.messagehandler;

import com.google.gson.Gson;
import com.seebattleserver.application.message.Message;
import org.springframework.web.socket.TextMessage;

public class MessageHandler {
    private Gson gson;

    public MessageHandler() {
        gson = new Gson();
    }

    public String handle(TextMessage text) {
        Message message = gson.fromJson(text.getPayload(), Message.class);
        String messageStr = message.getContent().trim();
        return messageStr;
    }
}
