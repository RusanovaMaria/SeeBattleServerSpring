package com.seebattleserver.application.controller;

import com.seebattleserver.application.gameregistry.GameRegistry;
import com.seebattleserver.application.user.User;
import com.seebattleserver.domain.game.ClassicGame;
import com.seebattleserver.domain.game.Game;
import com.seebattleserver.service.sender.UserSender;
import com.seebattleserver.service.websocket.registry.SessionRegistry;
import junit.framework.TestCase;
import org.mockito.Mockito;
import org.springframework.web.socket.WebSocketSession;

import static org.mockito.Mockito.*;

public class InvitationControllerTest extends TestCase {

    public void testHandle_withException() {
        User user = new User(null);
        User opponent = new User(null);
        WebSocketSession webSocketSession = mock(WebSocketSession.class);
        SessionRegistry sessionRegistry = new SessionRegistry();
        sessionRegistry.put(webSocketSession, user);
        sessionRegistry.put(webSocketSession, opponent);
        GameRegistry gameRegistry = new GameRegistry();
        Game game = new ClassicGame(user.getPlayer(), opponent.getPlayer());
        gameRegistry.put(user, game);
        gameRegistry.put(opponent, game);
        UserSender userSender = mock(UserSender.class);
        Controller invitationController = new InvitationController(user, userSender, gameRegistry);
        Controller spy = Mockito.spy(invitationController);
        doThrow(new IllegalArgumentException()).when(spy).handle("hgjhg");
    }

    public void testHandle_withPositiveResponse() {
        User user = new User(null);
        User opponent = new User(null);
        user.setOpponent(opponent);
        WebSocketSession webSocketSession = mock(WebSocketSession.class);
        SessionRegistry sessionRegistry = new SessionRegistry();
        sessionRegistry.put(webSocketSession, user);
        sessionRegistry.put(webSocketSession, opponent);
        GameRegistry gameRegistry = new GameRegistry();
        Game game = new ClassicGame(user.getPlayer(), opponent.getPlayer());
        gameRegistry.put(user, game);
        gameRegistry.put(opponent, game);
        UserSender userSender = mock(UserSender.class);
        Controller invitationController = new InvitationController(user, userSender, gameRegistry);
        Controller spy = Mockito.spy(invitationController);
        spy.handle("yes");
    }

    public void testHandle_withNegativeResponse() {
        User user = new User(null);
        User opponent = new User(null);
        user.setOpponent(opponent);
        WebSocketSession webSocketSession = mock(WebSocketSession.class);
        SessionRegistry sessionRegistry = new SessionRegistry();
        sessionRegistry.put(webSocketSession, user);
        sessionRegistry.put(webSocketSession, opponent);
        GameRegistry gameRegistry = new GameRegistry();
        Game game = new ClassicGame(user.getPlayer(), opponent.getPlayer());
        gameRegistry.put(user, game);
        gameRegistry.put(opponent, game);
        UserSender userSender = mock(UserSender.class);
        Controller invitationController = new InvitationController(user, userSender, gameRegistry);
        Controller spy = Mockito.spy(invitationController);
        spy.handle("no");
    }
}