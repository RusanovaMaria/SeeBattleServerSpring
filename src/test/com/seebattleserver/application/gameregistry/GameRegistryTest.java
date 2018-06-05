package com.seebattleserver.application.gameregistry;

import com.seebattleserver.application.user.User;
import com.seebattleserver.domain.game.ClassicGame;
import com.seebattleserver.domain.game.Game;
import com.seebattleserver.domain.player.Player;
import junit.framework.TestCase;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.spy;

public class GameRegistryTest extends TestCase {

    public void testRemove() {
        GameRegistry gameRegistry = new GameRegistry();
        GameRegistry spy = spy(gameRegistry);
        User user = new User(null);
        Game game = new ClassicGame(new Player(null), new Player(null));
        gameRegistry.put(user, game);
        gameRegistry.remove(user, game);
        doThrow(new IllegalArgumentException()).when(spy).getGameByUser(user);
    }

    public void testGetGameByUser_whenGameForUserExist_returnGame() {
        GameRegistry gameRegistry = new GameRegistry();
        Game game = new ClassicGame(new Player(null), new Player(null));
        User user = new User(null);
        gameRegistry.put(user, game);
        Game result = gameRegistry.getGameByUser(user);
        assertEquals(game, result);
    }

    public void testGetGameByUser_whenGameForUserDoesNotExist_returnException() {
        GameRegistry gameRegistry = new GameRegistry();
        GameRegistry spy = spy(gameRegistry);
        User user = new User(null);
        Game game = new ClassicGame(new Player(null), new Player(null));
        doThrow(new IllegalArgumentException()).when(spy).getGameByUser(user);
    }
}