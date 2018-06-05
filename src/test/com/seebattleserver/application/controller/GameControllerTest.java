package com.seebattleserver.application.controller;

import com.seebattleserver.application.gameregistry.GameRegistry;
import com.seebattleserver.application.user.User;
import com.seebattleserver.domain.game.ClassicGame;
import com.seebattleserver.domain.game.Game;
import com.seebattleserver.service.sender.UserSender;
import com.seebattleserver.service.websocket.registry.SessionRegistry;
import junit.framework.TestCase;
import org.springframework.web.socket.WebSocketSession;

import static org.mockito.Mockito.mock;

public class GameControllerTest extends TestCase {

    public void testHandle() {
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
        GameController gameController = new GameController(user, userSender, gameRegistry);
        gameController.handle("0a");
    }
}