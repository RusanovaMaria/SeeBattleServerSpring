package com.seebattleserver.application.gameregistry;

import com.seebattleserver.application.user.User;
import com.seebattleserver.domain.game.ClassicGame;
import com.seebattleserver.domain.game.Game;
import junit.framework.TestCase;
import org.junit.Before;
import org.mockito.Mock;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.MockitoAnnotations.initMocks;

public class GameRegistryTest extends TestCase {

    private GameRegistry gameRegistry;

    @Mock
    private User user;

    @Before
    public void setUp() {
        initMocks(this);
        gameRegistry = new GameRegistry();
    }

    public void testGetGameByUser_whenValidUser_returnGame() {
        Game game = mock(ClassicGame.class);
        gameRegistry.put(user, game);
        Game result = gameRegistry.getGameByUser(user);
        assertEquals(game, result);
    }

    public void testGetGameByUser_whenNotValidUser_returnIllegalArgumentException() {
        GameRegistry spy = spy(gameRegistry);
        doThrow(new IllegalArgumentException()).when(spy).getGameByUser(user);
    }
}