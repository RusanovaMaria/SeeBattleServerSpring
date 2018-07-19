package com.seebattleserver.service.websocket;

import com.google.gson.Gson;
import com.seebattleserver.application.controllermanager.ControllerManager;
import com.seebattleserver.application.user.UserRegistry;
import com.seebattleserver.service.websocket.registry.SessionRegistry;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class SocketHandlerTests {

    private SocketHandler socketHandler;

    private Gson gson;

    @Mock
    private TextMessage text;

    @Mock
    private SessionRegistry sessionRegistry;

    @Mock
    private UserRegistry userRegistry;

    @Mock
    private ControllerManager controllerManager;

    @Before
    public void setUp() {
        initMocks(this);
        socketHandler = new SocketHandler(sessionRegistry, userRegistry, gson, controllerManager);
        gson = new Gson();
    }

   /* @Test
    public void testHandleMessage_whenSessionContainsSessionRegistry_returnVerificationForHandleByControllerManager() throws IOException {
        final String TEST = "test";
        JsonMessage jsonmessage = new JsonMessage(TEST);
        String json = gson.toJson(jsonmessage);
        WebSocketSession session = mock(WebSocketSession.class);
        when(sessionRegistry.containsSession(session)).thenReturn(true);
        socketHandler.handleTextMessage(session, new TextMessage(json));
        verify(controllerManager).handle(any(User.class), json);
    }

    @Test
    public void testHandleMessage_whenSessionDoNotContainSessionRegistry_returnUser() throws IOException {
        final String TEST = "test";
        JsonMessage jsonmessage = new JsonMessage(TEST);
        String json = gson.toJson(jsonmessage);
        WebSocketSession session = mock(WebSocketSession.class);
        socketHandler.handleTextMessage(session, new TextMessage(json));
        verify(userRegistry).add(any(User.class));
        verify(sessionRegistry).put(eq(session), any(User.class));
    } */
}