package com.seebattleserver.application.controller;

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

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class GameControllerTest extends TestCase {

   private Controller controller;

   @Mock
   private User user;

   @Mock
   private User opponent;

   private Game game;

   @Mock
   private UserSender userSender;

   @Mock
   private GameRegistry gameRegistry;

   @Before
    public void setUp() {
       initMocks(this);
       game = mock(ClassicGame.class);
       when(gameRegistry.getGameByUser(user)).thenReturn(game);
       controller = new GameController(user, userSender, gameRegistry);
   }

   public void testHandle_whenUserStatusIsNotInGameMove_returnVerificationForGameNeverFire() {
       when(user.getUserStatus()).thenReturn(UserStatus.IN_GAME);
       controller.handle("0a");
       verify(game, never()).fire(any(Player.class), anyInt(), anyChar());
       verify(userSender).sendMessage(eq(user), any(Message.class));
   }

    public void testHandle_whenUserStatusIsInGameMove_returnVerificationForGameFire() {
        when(user.getUserStatus()).thenReturn(UserStatus.IN_GAME_MOVE);
        when(user.getOpponent()).thenReturn(opponent);
        Result result = Result.GOT;
        when(game.fire(any(Player.class), anyInt(), anyChar())).thenReturn(result);
        controller.handle("0a");
        verify(game).fire(any(Player.class), anyInt(), anyChar());
        verify(userSender).sendMessage(eq(user), any(Message.class));
    }

    public void testHandle_whenUserStatusIsNotValid_returnIllegalArgumentException() {
        when(user.getUserStatus()).thenReturn(UserStatus.FREE);
        Controller spy = spy(controller);
        doThrow( new IllegalArgumentException()).when(spy).handle("0a");
    }
}