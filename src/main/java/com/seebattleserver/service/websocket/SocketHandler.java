package com.seebattleserver.service.websocket;

import com.seebattleserver.application.client.Client;
import com.google.gson.Gson;
import com.seebattleserver.application.controller.ControllerManager;
import com.seebattleserver.application.user.User;
import com.seebattleserver.service.websocket.registry.SessionRegistry;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import com.seebattleserver.application.message.Message;

import java.io.IOException;

public class SocketHandler extends TextWebSocketHandler {

    private SessionRegistry sessionRegistry;
    private Gson gson;

    public SocketHandler(SessionRegistry sessionRegistry, Gson gson) {
        this.sessionRegistry = sessionRegistry;
        this.gson = gson;
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage context) throws IOException {
        if (sessionRegistry.containsSession(session)) {
            User user = sessionRegistry.getUser(session);
            String command = readMessageInSession(session, context);
            ControllerManager commandController = new ControllerManager(client);
            commandController.handle(command);
        } else {
            Message name = gson.fromJson(context.getPayload(), Message.class);
            Client client = new Client(session);
            client.setName(name.getMessage());
            sessionRegistry.put(session, new User(name.getMessage()));
        }
    }

    public void sendMessageInSession(WebSocketSession session,String context) throws IOException {
        Message message = new Message(context);
        String messageJson = gson.toJson(message);
        session.sendMessage(new TextMessage(messageJson));
    }

    public String readMessageInSession(WebSocketSession session, TextMessage context) {
        Message message = gson.fromJson(context.getPayload(), Message.class);
        return message.getContext();
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sendMessageInSession(session, "Введите свое имя");
    }
}