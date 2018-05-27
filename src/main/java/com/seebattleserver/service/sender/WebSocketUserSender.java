package com.seebattleserver.service.sender;

import com.google.gson.Gson;
import com.seebattleserver.application.user.User;
import com.seebattleserver.service.message.Message;
import com.seebattleserver.service.websocket.registry.SessionRegistry;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

public class WebSocketUserSender implements UserSender {

    private SessionRegistry sessionRegistry;
    private Gson gson;

    public WebSocketUserSender(SessionRegistry sessionRegistry, Gson gson) {
        this.sessionRegistry = sessionRegistry;
        this.gson = gson;
    }

    @Override
    public void sendMessage(User user, Message message) {
        try {
            String messageStr = gson.toJson(message);
            WebSocketSession session = sessionRegistry.getSession(user);
            session.sendMessage(new TextMessage(messageStr));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
