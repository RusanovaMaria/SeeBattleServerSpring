package com.seebattleserver.service.websocket;

import com.google.gson.Gson;
import com.seebattleserver.application.controllermanager.ControllerManager;
import com.seebattleserver.application.json.jsonmessage.JsonMessage;
import com.seebattleserver.application.user.User;
import com.seebattleserver.application.user.UserRegistry;
import com.seebattleserver.service.sender.UserSender;
import com.seebattleserver.service.websocket.registry.SessionRegistry;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class SocketHandlerTest {
    private SocketHandler socketHandler;
    private Gson gson;

    @Mock
    private SessionRegistry sessionRegistry;

    @Mock
    private UserRegistry userRegistry;

    @Mock
    private ControllerManager controllerManager;

    @Before
    public void setUp() {
        initMocks(this);
        gson = new Gson();
        socketHandler = new SocketHandler(sessionRegistry, userRegistry, gson, controllerManager);
    }

    @Test
    public void handleTextMessage_whenTextMessageAndWebSocketSessionAreNotNull_verifyControllerManagerHandle() {
        String test = "test";
        JsonMessage jsonmessage = new JsonMessage(test);
        String json = gson.toJson(jsonmessage);
        TextMessage textMessage = new TextMessage(json);
        WebSocketSession session = mock(WebSocketSession.class);
        socketHandler.handleTextMessage(session, textMessage);
        verify(controllerManager).handle(any(User.class), eq(textMessage));
    }

    @Test
    public void handleTextMessage_whenTextMessageAndSessionAreNull_verifyControllerManagerHandle() {
        TextMessage textMessage = null;
        WebSocketSession session = null;
        socketHandler.handleTextMessage(session, textMessage);
        verify(controllerManager).handle(any(User.class), eq(textMessage));
    }

    @Test
    public void afterConnectionEstablished_whenWebSocketSessionIsNotNull_verifySessionRegistryPutUserAndSessionAndSessionSendMessage()
            throws Exception {
        WebSocketSession session = mock(WebSocketSession.class);
        socketHandler.afterConnectionEstablished(session);
        verify(sessionRegistry).put(eq(session), any(User.class));
        verify(session).sendMessage(any(TextMessage.class));
    }
}