package com.seebattleserver.service.websocket;

import com.seebattleserver.application.client.Client;
import com.seebattleserver.application.client.ClientSet;
import com.google.gson.Gson;
import com.seebattleserver.application.controller.ControllerManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import com.seebattleserver.service.message.Message;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class SocketHandler extends TextWebSocketHandler {

    @Autowired
    public SocketHandler() {

    }

    private ClientSet clientSet = new ClientSet();
    private Gson gson = new Gson();
    private  List<WebSocketSession> sessions = new CopyOnWriteArrayList<WebSocketSession>();

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage context) throws IOException {
        if (sessions.contains(session)) {
            Client client = clientSet.findClientByWebSocketSession(session);
            String command = readMessageInSession(session, context);
            ControllerManager commandController = new ControllerManager(client);
            commandController.handle(command);
        } else {
          Message name = gson.fromJson(context.getPayload(), Message.class);
            Client client = new Client(session);
            client.setName(name.getContext());
            clientSet.add(client);
            sessions.add(session);
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
        //sessions.add(session);
        sendMessageInSession(session, "Введите свое имя");
    }
}