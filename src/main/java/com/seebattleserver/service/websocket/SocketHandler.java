package com.seebattleserver.service.websocket;

import com.google.gson.Gson;
import com.seebattleserver.application.controllermanager.ControllerManager;
import com.seebattleserver.application.json.jsonmessage.JsonMessage;
import com.seebattleserver.application.json.jsonmessage.jsonmessagehandler.DefaultJsonMessageHandler;
import com.seebattleserver.application.user.User;
import com.seebattleserver.application.user.UserRegistry;
import com.seebattleserver.service.websocket.registry.SessionRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;


import java.io.IOException;

@Component
public class SocketHandler extends TextWebSocketHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(SocketHandler.class);
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
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage textMessage) {
        User user = sessionRegistry.getUser(session);
        controllerManager.handle(user, textMessage);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        LOGGER.info("Подключение нового клиента");
        createUserAndRegisterSession(session);
        sendMessageInSession(session, "Введите свое имя");
    }

    private void createUserAndRegisterSession(WebSocketSession session) {
        User user = new User();
        sessionRegistry.put(session, user);
    }

    private void sendMessageInSession(WebSocketSession session, String context) throws IOException {
        JsonMessage jsonMessage = new JsonMessage(context);
        String messageJson = gson.toJson(jsonMessage);
        session.sendMessage(new TextMessage(messageJson));
    }
}