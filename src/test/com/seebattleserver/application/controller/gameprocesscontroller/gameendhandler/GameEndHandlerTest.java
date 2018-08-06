package com.seebattleserver.application.controller.gameprocesscontroller.gameendhandler;

import com.seebattleserver.application.gameregistry.GameRegistry;
import com.seebattleserver.application.json.jsonmessage.JsonMessage;
import com.seebattleserver.application.user.User;
import com.seebattleserver.application.user.UserStatus;
import com.seebattleserver.domain.game.ClassicGame;
import com.seebattleserver.domain.game.Game;
import com.seebattleserver.domain.playingfield.ClassicPlayingField;
import com.seebattleserver.domain.playingfield.PlayingField;
import com.seebattleserver.service.sender.UserSender;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class GameEndHandlerTest {
    private GameEndHandler gameEndHandler;
    private User spyFirstUser;
    private User spySecondUser;
    private PlayingField spyFirstPlayingField;
    private PlayingField spySecondPlayingField;
    private Game spyGame;

    @Mock
    private GameRegistry gameRegistry;

    @Mock
    UserSender userSender;

    @Before
    public void setUp() {
        initMocks(this);
        User firstUser = new User();
        User secondUser = new User();
        firstUser.setUserOpponent(secondUser);
        secondUser.setUserOpponent(firstUser);
        spyFirstPlayingField = spy(new ClassicPlayingField());
        spySecondPlayingField = spy (new ClassicPlayingField());
        firstUser.getPlayer().setPlayingField(spyFirstPlayingField);
        secondUser.getPlayer().setPlayingField(spySecondPlayingField);
        spyFirstUser = spy(firstUser);
        spySecondUser = spy(secondUser);
        spyGame = spy(new ClassicGame(spyFirstUser.getPlayer(), spySecondUser.getPlayer()));
        gameEndHandler = new GameEndHandler(spyFirstUser, spySecondUser, spyGame, gameRegistry, userSender);
    }

    @Test
    public void endGame_whenOneOfPlayingFieldsHasAllGameObjectsDied_returnUsersStatusesAndOpponentAndVerifyGameRemoveFromGameRegistry() {
        when(spyFirstPlayingField.allGameObjectsDied()).thenReturn(true);
        gameEndHandler.endGame();
        assertEquals(UserStatus.FREE, spyFirstUser.getUserStatus());
        assertEquals(UserStatus.FREE, spySecondUser.getUserStatus());
        assertNull(spyFirstUser.getUserOpponent());
        assertNull(spySecondUser.getUserOpponent());
        verify(gameRegistry).remove(spyFirstUser, spyGame);
        verify(gameRegistry).remove(spySecondUser, spyGame);
    }

    @Test(expected = IllegalStateException.class)
    public void endGame_whenNoOneOfPlayingFieldsHasAllGameObjectsDied_returnIllegalStateException() {
        gameEndHandler.endGame();
    }
}