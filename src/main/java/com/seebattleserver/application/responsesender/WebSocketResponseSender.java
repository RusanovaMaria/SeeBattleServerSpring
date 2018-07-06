package com.seebattleserver.application.responsesender;

import com.google.gson.Gson;
import com.seebattleserver.application.message.Message;
import com.seebattleserver.application.user.User;
import com.seebattleserver.service.websocket.registry.SessionRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.List;

@Component
public class WebSocketResponseSender implements ResponseSender {
    private SessionRegistry sessionRegistry;
    private Gson gson;

    @Autowired
    public WebSocketResponseSender(SessionRegistry sessionRegistry, Gson gson) {
        this.sessionRegistry = sessionRegistry;
        this.gson = gson;
    }

    @Override
    public void sendResponse(List<Message> response) {
       for (int i = 0; i < response.size(); i++) {
           Message message = response.get(i);
           sendMessage(message);
       }
    }

    private void sendMessage(Message message) {
        try {
            String messageStr = gson.toJson(message);
            User user = message.getTo();
            WebSocketSession session = sessionRegistry.getSession(user);
            session.sendMessage(new TextMessage(messageStr));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
