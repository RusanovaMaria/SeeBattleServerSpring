package com.seebattleserver.application.controller.gameobjectarrangementcontroller;

import com.google.gson.Gson;
import com.seebattleserver.application.controller.gameobjectarrangementcontroller.gamestarthandler.ClassicGameStartHandler;
import com.seebattleserver.application.gameregistry.GameRegistry;
import com.seebattleserver.application.json.jsonmessage.JsonMessage;
import com.seebattleserver.application.user.User;
import com.seebattleserver.application.user.UserStatus;
import com.seebattleserver.domain.cage.Cage;
import com.seebattleserver.domain.cage.State;
import com.seebattleserver.domain.playingfield.ClassicPlayingField;
import com.seebattleserver.domain.playingfield.PlayingField;
import com.seebattleserver.service.sender.UserSender;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.springframework.web.socket.TextMessage;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class GameObjectArrangementControllerTest {
    private GameObjectArrangementController gameObjectArrangementController;
    private PlayingField playingField;
    private User spyUser;
    private Gson gson;

    @Mock
    private GameRegistry gameRegistry;

    @Mock
    private UserSender userSender;

    @Before
    public void setUp() {
        initMocks(this);
        User user = new User();
        playingField = new ClassicPlayingField();
        user.getPlayer().setPlayingField(playingField);
        User userOpponent = new User();
        user.setUserOpponent(userOpponent);
        userOpponent.setUserOpponent(userOpponent);
        spyUser = spy(user);
        gson = new Gson();
        gameObjectArrangementController = new GameObjectArrangementController(spyUser, userSender, gameRegistry);
    }

    @Test
    public void handle_whenUserChoseDefaultGameObjectArrangement_returnPlayingFieldCageStateAndUserStatus() {
        JsonMessage jsonMessage = new JsonMessage("default");
        String jsonString = gson.toJson(jsonMessage);
        TextMessage textMessage = new TextMessage(jsonString);
        gameObjectArrangementController.handle(textMessage);
        Cage cage = playingField.identifyCage(0, 'a');
        assertEquals(State.FULL, cage.getState());
        assertEquals(UserStatus.IN_GAME_MOVE, spyUser.getUserStatus());
    }

    @Test
    public void handle_whenUserChoseUserGameObjectArrangement_returnUserStatusAndVerifySendMessageToUser() {
        JsonMessage jsonMessage = new JsonMessage("user");
        String jsonString = gson.toJson(jsonMessage);
        TextMessage textMessage = new TextMessage(jsonString);
        gameObjectArrangementController.handle(textMessage);
        assertEquals(UserStatus.SET_UP_GAME_OBJECTS, spyUser.getUserStatus());
        verify(userSender).sendMessage(Matchers.eq(spyUser), any(JsonMessage.class));
    }

    @Test
    public void handle_whenUserChoseNotCorrectGameObjectArrangement_returnUserStatusAndVerifySEndMessageToUser() {
        JsonMessage jsonMessage = new JsonMessage("not valid game object arrangement type");
        String jsonString = gson.toJson(jsonMessage);
        TextMessage textMessage = new TextMessage(jsonString);
        gameObjectArrangementController.handle(textMessage);
        assertNotEquals(UserStatus.READY_FOR_GAME, spyUser.getUserStatus());
        assertNotEquals(UserStatus.IN_GAME_MOVE, spyUser.getUserStatus());
        assertNotEquals(UserStatus.IN_GAME, spyUser.getUserStatus());
        verify(userSender).sendMessage(Matchers.eq(spyUser), any(JsonMessage.class));
    }
}