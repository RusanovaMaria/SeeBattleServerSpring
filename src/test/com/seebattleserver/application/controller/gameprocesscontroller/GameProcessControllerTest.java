package com.seebattleserver.application.controller.gameprocesscontroller;

import com.google.gson.Gson;
import com.seebattleserver.application.gameregistry.GameRegistry;
import com.seebattleserver.application.json.jsonmessage.JsonMessage;
import com.seebattleserver.application.user.User;
import com.seebattleserver.application.user.UserStatus;
import com.seebattleserver.domain.game.ClassicGame;
import com.seebattleserver.domain.game.Game;
import com.seebattleserver.domain.gameobjectarrangement.defaultgameobjectarrangement.ClassicDefaultGameObjectArrangement;
import com.seebattleserver.domain.gameobjectarrangement.defaultgameobjectarrangement.DefaultGameObjectArrangement;
import com.seebattleserver.domain.playingfield.ClassicPlayingField;
import com.seebattleserver.domain.playingfield.PlayingField;
import com.seebattleserver.service.sender.UserSender;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.web.socket.TextMessage;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class GameProcessControllerTest {
    private GameProcessController gameProcessController;
    private User spyUser;
    private Gson gson;
    private Game spyGame;

    @Mock
    private GameRegistry gameRegistry;

    @Mock
    private UserSender userSender;

    @Before
    public void setUp() {
        initMocks(this);
        User user = new User();
        User userOpponent = new User();
        PlayingField userOpponentPlayingField = new ClassicPlayingField();
        DefaultGameObjectArrangement gameObjectArrangement = new ClassicDefaultGameObjectArrangement();
        gameObjectArrangement.arrangeGameObjectsByDefault(userOpponentPlayingField);
        userOpponent.getPlayer().setPlayingField(userOpponentPlayingField);
        PlayingField userPlayingField = new ClassicPlayingField();
        user.getPlayer().setPlayingField(userPlayingField);
        user.setUserOpponent(userOpponent);
        userOpponent.setUserOpponent(user);
        user.setUserStatus(UserStatus.IN_GAME_MOVE);
        Game game = new ClassicGame(user.getPlayer(), userOpponent.getPlayer());
        spyGame = spy(game);
        spyUser = spy(user);
        gson = new Gson();
        when(gameRegistry.getGameByUser(spyUser)).thenReturn(spyGame);
        gameProcessController = new GameProcessController(spyUser, gameRegistry, userSender);
    }

    @Test
    public void handle_whenUserEnteredCorrectCoordinates_returnUserStatusAndVerifySendMessageToUser() {
        JsonMessage jsonMessage = new JsonMessage("0a");
        String jsonString = gson.toJson(jsonMessage);
        TextMessage textMessage = new TextMessage(jsonString);
        gameProcessController.handle(textMessage);
        assertEquals(UserStatus.IN_GAME, spyUser.getUserStatus());
        verify(userSender).sendMessage(eq(spyUser), any(JsonMessage.class));
    }

    @Test
    public void handle_whenUserEnteredNotCorrectCoordinates_returnUserStatusAndVerifySendMessageToUser() {
        JsonMessage jsonMessage = new JsonMessage("aa");
        String jsonString = gson.toJson(jsonMessage);
        TextMessage textMessage = new TextMessage(jsonString);
        gameProcessController.handle(textMessage);
        assertEquals(UserStatus.IN_GAME_MOVE, spyUser.getUserStatus());
        verify(userSender).sendMessage(eq(spyUser), any(JsonMessage.class));
    }
}