package com.seebattleserver.service.websocket;

import com.google.gson.Gson;
import com.seebattleserver.application.controller.ControllerManager;
import com.seebattleserver.application.user.User;
import com.seebattleserver.application.user.UserRegistry;
import com.seebattleserver.configuration.UtilConfiguration;
import com.seebattleserver.service.sender.UserSender;
import com.seebattleserver.service.websocket.registry.SessionRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import com.seebattleserver.application.message.Message;

import java.io.IOException;

@Component
public class SocketHandler extends TextWebSocketHandler {

    protected UserRegistry userRegistry;
    private SessionRegistry sessionRegistry;
    private Gson gson;
    private ControllerManager controllerManager;

    @Autowired
    public SocketHandler(SessionRegistry sessionRegistry, UserRegistry userRegistry, Gson gson, ControllerManager controllerManager) {
        this.sessionRegistry = sessionRegistry;
        this.userRegistry = userRegistry;
        this.gson = gson;
        this.controllerManager = controllerManager;

        ApplicationContext context = new AnnotationConfigApplicationContext(UtilConfiguration.class);
        this.gson = context.getBean(Gson.class);
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage context) throws IOException {

        if (sessionRegistry.containsSession(session)) {
            User user = sessionRegistry.getUser(session);
            Message message = gson.fromJson(context.getPayload(), Message.class);
            controllerManager.handle(user, message.getMessage());

        } else {
            Message name = gson.fromJson(context.getPayload(), Message.class);
            User user = new User(name.getMessage());
            userRegistry.add(user);
            sessionRegistry.put(session, user);
        }
    }

    private void sendMessageInSession(WebSocketSession session, String context) throws IOException {
        Message message = new Message(context);
        String messageJson = gson.toJson(message);
        session.sendMessage(new TextMessage(messageJson));
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sendMessageInSession(session, "Введите свое имя");
    }
}