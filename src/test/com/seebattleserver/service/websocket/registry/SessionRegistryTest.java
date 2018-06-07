package com.seebattleserver.service.websocket.registry;

import com.seebattleserver.application.user.User;
import junit.framework.TestCase;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.web.socket.WebSocketSession;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

public class SessionRegistryTest extends TestCase {

    @Test
    public void testGetSession_whenUserIsExists_returnSession() {
        User user = new User(null);
        WebSocketSession webSocketSession = mock(WebSocketSession.class);
        SessionRegistry sessionRegistry = new SessionRegistry();
        sessionRegistry.put(webSocketSession, user);
        WebSocketSession result = sessionRegistry.getSession(user);
        assertEquals(webSocketSession, result);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testGetSession_whenUserDoNotExist_returnException() {
        User user1 = new User(null);
        User user2 = new User(null);
        SessionRegistry sessionRegistry = new SessionRegistry();
        SessionRegistry spy = Mockito.spy(sessionRegistry);
        WebSocketSession webSocketSession = mock(WebSocketSession.class);
        spy.put(webSocketSession, user1);
        doThrow(new IllegalArgumentException()).when(spy).getSession(user2);
    }

    @Test
    public void testGetSession_whenUserIsNull_returnSession() {
        SessionRegistry sessionRegistry = new SessionRegistry();
        SessionRegistry spy = Mockito.spy(sessionRegistry);
        WebSocketSession webSocketSession = mock(WebSocketSession.class);
        spy.put(webSocketSession, null);
        doThrow(new IllegalArgumentException()).when(spy).getSession(null);
    }
}