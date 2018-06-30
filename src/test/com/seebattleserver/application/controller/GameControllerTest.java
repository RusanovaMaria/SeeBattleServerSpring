package com.seebattleserver.application.controller;

import com.google.gson.Gson;
import com.seebattleserver.application.gameregistry.GameRegistry;
import com.seebattleserver.application.message.Message;
import com.seebattleserver.application.user.User;
import com.seebattleserver.application.user.UserStatus;
import com.seebattleserver.domain.game.ClassicGame;
import com.seebattleserver.domain.game.Game;
import com.seebattleserver.domain.game.Result;
import com.seebattleserver.domain.player.Player;
import com.seebattleserver.service.sender.UserSender;
import junit.framework.TestCase;
import org.junit.Before;
import org.mockito.Mock;
import org.springframework.web.socket.TextMessage;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class GameControllerTest extends TestCase {

    private static final String TEST_COORDINATE = "0a";

    private Controller controller;

    private TextMessage text;

    @Mock
    private User user;

    @Mock
    private User opponent;

    private Game game;

    @Mock
    private UserSender userSender;

    @Mock
    private GameRegistry gameRegistry;

    private Gson gson;

    @Before
    public void setUp() {
        initMocks(this);
        gson = new Gson();
        game = mock(ClassicGame.class);
        when(gameRegistry.getGameByUser(user)).thenReturn(game);
        controller = new GameController(user, userSender, gameRegistry, gson);
        text = new TextMessage(TEST_COORDINATE);
    }

    public void testHandle_whenUserStatusIsNotInGameMove_returnVerificationForGameNeverFire() {
        when(user.getUserStatus()).thenReturn(UserStatus.IN_GAME);
        controller.handle(text);
        verify(game, never()).fire(any(Player.class), anyInt(), anyChar());
        verify(userSender).sendMessage(eq(user), any(Message.class));
    }

    public void testHandle_whenUserStatusIsInGameMove_returnVerificationForGameFire() {
        when(user.getUserStatus()).thenReturn(UserStatus.IN_GAME_MOVE);
        when(user.getOpponent()).thenReturn(opponent);
        Result result = Result.GOT;
        when(game.fire(any(Player.class), anyInt(), anyChar())).thenReturn(result);
        controller.handle(text);
        verify(game).fire(any(Player.class), anyInt(), anyChar());
        verify(userSender).sendMessage(eq(user), any(Message.class));
    }

    public void testHandle_whenUserStatusIsNotValid_returnIllegalArgumentException() {
        when(user.getUserStatus()).thenReturn(UserStatus.FREE);
        Controller spy = spy(controller);
        doThrow(new IllegalArgumentException()).when(spy).handle(text);
    }
}