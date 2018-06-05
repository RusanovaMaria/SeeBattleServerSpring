package com.seebattleserver.application.controller;

import com.google.gson.Gson;
import com.seebattleserver.application.command.CommandFactory;
import com.seebattleserver.application.gameregistry.GameRegistry;
import com.seebattleserver.application.user.User;
import com.seebattleserver.application.user.UserRegistry;
import com.seebattleserver.service.sender.UserSender;
import com.seebattleserver.service.sender.WebSocketUserSender;
import com.seebattleserver.service.websocket.registry.SessionRegistry;
import junit.framework.TestCase;
import org.springframework.web.socket.WebSocketSession;

import static org.mockito.Mockito.*;

public class ControllerManagerTest extends TestCase {

    public void testHandle() {
        User user = new User(null);
        WebSocketSession webSocketSession = mock(WebSocketSession.class);
        SessionRegistry sessionRegistry = new SessionRegistry();
        sessionRegistry.put(webSocketSession, user);
        Gson gson = new Gson();
        UserSender userSender = new WebSocketUserSender(sessionRegistry, gson);
        GameRegistry gameRegistry = mock(GameRegistry.class);
        UserRegistry userRegistry = mock(UserRegistry.class);
        CommandFactory commandFactory = new CommandFactory(userRegistry);
        ControllerFactory controllerFactory = new ControllerFactory(userSender, commandFactory,gameRegistry,userRegistry);
        ControllerManager controllerManager = new ControllerManager(controllerFactory);
        ControllerManager spy = spy(controllerManager);
        spy.handle(user, "help");
    }
}