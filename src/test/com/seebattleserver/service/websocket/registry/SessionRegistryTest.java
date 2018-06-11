package com.seebattleserver.service.websocket.registry;

import com.seebattleserver.application.user.User;
import junit.framework.TestCase;
import org.junit.Before;
import org.mockito.Mock;
import org.springframework.web.socket.WebSocketSession;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class SessionRegistryTest extends TestCase {

    private SessionRegistry sessionRegistry;

    @Mock
    private User user;

    @Mock
    private WebSocketSession session;

    @Before
    public void setUp() {
        initMocks(this);
        sessionRegistry = new SessionRegistry();
        sessionRegistry.put(session, user);
    }

    public void testGetUser_whenUserExists_returnUser() {
        User result = sessionRegistry.getUser(session);
        assertEquals(user, result);
    }

    public void testGetUser_whenUserDoNotExist_returnNull() {
        WebSocketSession newSession = mock(WebSocketSession.class);
        User result = sessionRegistry.getUser(newSession);
        assertNull(result);
    }

    public void testGetSession_whenSessionExists_returnSession() {
        WebSocketSession result = sessionRegistry.getSession(user);
        assertEquals(session, result);
    }

    public void testGetSession_whenSessionDoNotExist_returnIllegalArgumentException() {
        User newUser = mock(User.class);
        SessionRegistry spy = spy(sessionRegistry);
        doThrow(new IllegalArgumentException()).when(spy).getSession(newUser);
    }

    public void testContainsSession_whenSessionIsExists_returnTrue() {
        boolean result = sessionRegistry.containsSession(session);
        assertTrue(result);
    }

    public void testContainsSession_whenSessionDoNotExist_returnFalse() {
        WebSocketSession newSession = mock(WebSocketSession.class);
        boolean result = sessionRegistry.containsSession(newSession);
        assertFalse(result);
    }
}