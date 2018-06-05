package com.seebattleserver.application.controller;

import com.seebattleserver.application.command.CommandFactory;
import com.seebattleserver.application.command.HelpCommand;
import com.seebattleserver.application.user.User;
import com.seebattleserver.application.user.UserRegistry;
import com.seebattleserver.service.sender.UserSender;
import com.seebattleserver.service.websocket.registry.SessionRegistry;
import junit.framework.TestCase;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.web.socket.WebSocketSession;

import static org.mockito.Mockito.*;

public class CommandControllerTest extends TestCase {

    @Test (expected = IllegalArgumentException.class)
    public void testHandle() {
        User user = new User(null);
        WebSocketSession webSocketSession = mock(WebSocketSession.class);
        SessionRegistry sessionRegistry = new SessionRegistry();
        sessionRegistry.put(webSocketSession, user);
        UserSender userSender = mock(UserSender.class);
        UserRegistry userRegistry = new UserRegistry();
        userRegistry.add(user);
        CommandFactory commandFactory = new CommandFactory(userRegistry);
        CommandController commandController = new CommandController(user, userSender, commandFactory);
        commandController.handle("help");
    }

    public void testHandle_WithException() {
        User user = new User(null);
        WebSocketSession webSocketSession = mock(WebSocketSession.class);
        SessionRegistry sessionRegistry = new SessionRegistry();
        sessionRegistry.put(webSocketSession, user);
        UserSender userSender = mock(UserSender.class);
        UserRegistry userRegistry = new UserRegistry();
        userRegistry.add(user);
        CommandFactory commandFactory = new CommandFactory(userRegistry);
        CommandController commandController = new CommandController(user, userSender, commandFactory);
        CommandController spy = Mockito.spy(commandController);
        doThrow(new IllegalArgumentException()).when(spy).handle("fvdfv");
    }
}