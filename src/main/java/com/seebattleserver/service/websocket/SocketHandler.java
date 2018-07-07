package com.seebattleserver.service.websocket;

import com.google.gson.Gson;
import com.seebattleserver.application.controller.controllermanager.ControllerManager;
import com.seebattleserver.application.user.User;
import com.seebattleserver.application.user.UserRegistry;
import com.seebattleserver.configuration.UtilConfiguration;
import com.seebattleserver.service.websocket.registry.SessionRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

        ApplicationContext context = new AnnotationConfigApplicationContext(UtilConfiguration.class);
        this.gson = context.getBean(Gson.class);
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage text) throws IOException {
        if (isNotNewSession(session)) {
            handleUserMessage(text, session);
        } else {
            registerUser(text, session);
            sendMessageInSession(session, "Регистрация успешно завершена. " +
                    "Введите команду help, чтобы посмотреть список возможных команд.");
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        LOGGER.info("Подключение нового клиента");
        sendMessageInSession(session, "Введите свое имя");
    }

    private boolean isNotNewSession(WebSocketSession session) {
        if (sessionRegistry.containsSession(session)) {
            return true;
        }
        return false;
    }

    private void handleUserMessage(TextMessage text, WebSocketSession session) {
        User user = sessionRegistry.getUser(session);
        controllerManager.handle(user, text);
    }

    private void registerUser(TextMessage text, WebSocketSession session) {
        Message name = gson.fromJson(text.getPayload(), Message.class);
        User user = new User(name.getContent());
        userRegistry.add(user);
        sessionRegistry.put(session, user);
    }

    private void sendMessageInSession(WebSocketSession session, String context) throws IOException {
        Message message = new Message(context);
        String messageJson = gson.toJson(message);
        session.sendMessage(new TextMessage(messageJson));
    }
}