package com.seebattleserver.application.controller.gameobjectarrangementcontroller.gamestarthandler;

import com.google.gson.Gson;
import com.seebattleserver.application.gameregistry.GameRegistry;
import com.seebattleserver.application.json.jsonmessage.JsonMessage;
import com.seebattleserver.application.user.User;
import com.seebattleserver.application.user.UserStatus;
import com.seebattleserver.domain.game.ClassicGame;
import com.seebattleserver.service.sender.UserSender;
import com.seebattleserver.service.sender.WebSocketUserSender;
import com.seebattleserver.service.websocket.registry.SessionRegistry;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class ClassicGameStartHandlerTest {
    private GameStartHandler gameStartHandler;
    private User user;
    private User userOpponent;

    @Mock
    private GameRegistry gameRegistry;

    @Mock
    private UserSender userSender;

    @Before
    public void setUp() {
      initMocks(this);
        user = new User();
        userOpponent = new User();
    }

    @Test
    public void startGameIfPossible_whenUserOpponentStatusIsInGameMove_verifyUserSetStatusInGameAndGamePutInGameRegistryAndSendMessageToUser() {
        User spyUser = spy(user);
        userOpponent.setUserStatus(UserStatus.IN_GAME_MOVE);
        User spyUserOpponent = spy(userOpponent);
        spyUser.setUserOpponent(spyUserOpponent);
        spyUserOpponent.setUserOpponent(spyUser);
        gameStartHandler = new ClassicGameStartHandler(spyUser, gameRegistry, userSender);
        gameStartHandler.startGameIfPossible();
        verify(spyUser).setUserStatus(UserStatus.IN_GAME);
        verify(gameRegistry).put(eq(spyUser), any(ClassicGame.class));
        verify(gameRegistry).put(eq(spyUserOpponent), any(ClassicGame.class));
        verify(userSender).sendMessage(eq(spyUser), any(JsonMessage.class));
        verify(userSender).sendMessage(eq(spyUserOpponent), any(JsonMessage.class));
    }

    @Test
    public void startGameIfPossible_whenUserOpponentStatusIsNotInGameMove_verifyUserSetStatusInGameMoveAndSendMessageToUser() {
        User spyUser = spy(user);
        User spyUserOpponent = spy(userOpponent);
        spyUser.setUserOpponent(spyUserOpponent);
        spyUserOpponent.setUserOpponent(spyUser);
        gameStartHandler = new ClassicGameStartHandler(spyUser, gameRegistry, userSender);
        gameStartHandler.startGameIfPossible();
        verify(spyUser).setUserStatus(UserStatus.IN_GAME_MOVE);
        verify(userSender, times(2)).sendMessage(eq(spyUser), any(JsonMessage.class));
    }
}