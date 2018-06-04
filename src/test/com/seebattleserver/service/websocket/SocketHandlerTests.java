package com.seebattleserver.service.websocket;

import com.google.gson.Gson;
import com.seebattleserver.application.message.Message;
import com.seebattleserver.application.user.UserRegistry;
import com.seebattleserver.service.sender.UserSender;
import com.seebattleserver.service.websocket.registry.SessionRegistry;
import org.junit.Test;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SocketHandlerTests {


    @Test
    public void handleTextMessage() throws IOException {
        SessionRegistry sessionRegistry = mock(SessionRegistry.class);
        UserRegistry userRegistry = mock(UserRegistry.class);
        UserSender userSender = mock(UserSender.class);
        Gson gson = new Gson();
        SocketHandler socketHandler = new SocketHandler(sessionRegistry, userRegistry, userSender, new Gson());
        mock(WebSocketSession.class);
        WebSocketSession webSocketSession = mock(WebSocketSession.class);
        when(sessionRegistry.containsSession(webSocketSession)).thenReturn(true);
        socketHandler.handleTextMessage(webSocketSession, new TextMessage(gson.toJson(new Message("hi"))));
    }

}