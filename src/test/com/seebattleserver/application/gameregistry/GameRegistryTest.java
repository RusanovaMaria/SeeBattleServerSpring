package com.seebattleserver.application.gameregistry;

import com.seebattleserver.application.user.User;
import com.seebattleserver.domain.game.ClassicGame;
import com.seebattleserver.domain.game.Game;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GameRegistryTest {
    private GameRegistry gameRegistry;
    private User firstUser;
    private User secondUser;
    private Game game;

    @Before
    public void setUp() {
        gameRegistry = new GameRegistry();
        firstUser = new User();
        secondUser = new User();
        game = new ClassicGame(firstUser.getPlayer(), secondUser.getPlayer());
    }

    @Test
    public void getGameByUser_whenUsersInGameAndGameInGameRegistry_returnGame() {
        gameRegistry.put(firstUser, game);
        gameRegistry.put(secondUser, game);
        assertEquals(game ,gameRegistry.getGameByUser(firstUser));
        assertEquals(game ,gameRegistry.getGameByUser(secondUser));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getGameByUser_whenUserNotInGameAndGameRegistryIsEmpty_returnIllegalArgumentException() {
        User newUser = new User();
        gameRegistry.getGameByUser(newUser);
    }
}