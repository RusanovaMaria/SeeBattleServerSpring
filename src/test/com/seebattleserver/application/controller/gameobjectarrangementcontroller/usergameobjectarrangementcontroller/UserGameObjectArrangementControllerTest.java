package com.seebattleserver.application.controller.gameobjectarrangementcontroller.usergameobjectarrangementcontroller;

import com.google.gson.Gson;
import com.seebattleserver.application.gameregistry.GameRegistry;
import com.seebattleserver.application.json.jsongameobjectcoordinates.JsonGameObjectCoordinates;
import com.seebattleserver.application.json.jsonmessage.JsonMessage;
import com.seebattleserver.application.user.User;
import com.seebattleserver.domain.cage.Cage;
import com.seebattleserver.domain.cage.State;
import com.seebattleserver.domain.coordinatecouple.CoordinatesCouple;
import com.seebattleserver.domain.playingfield.ClassicPlayingField;
import com.seebattleserver.domain.playingfield.PlayingField;
import com.seebattleserver.service.sender.UserSender;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.web.socket.TextMessage;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class UserGameObjectArrangementControllerTest {
    private UserGameObjectArrangementController userGameObjectArrangementController;
    private User spyUser;
    private PlayingField playingField;
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
        spyUser = spy(user);
        gson = new Gson();
        userGameObjectArrangementController = new UserGameObjectArrangementController(spyUser, gameRegistry, userSender);
    }

    @Test
    public void handle_whenGameObjectCoordinatesAreValid_returnPlayingFieldCageStateAndVerifySendMessageToUser() {
        int x = 1;
        char y = 'a';
        List<List<CoordinatesCouple>> gameObjectsCoordinates = new ArrayList<>();
        CoordinatesCouple coordinatesCouple = new CoordinatesCouple(x, y);
        List<CoordinatesCouple> gameObjectCoordinatesBySizeOne = new ArrayList<>();
        gameObjectCoordinatesBySizeOne.add(coordinatesCouple);
        gameObjectsCoordinates.add(gameObjectCoordinatesBySizeOne);
        JsonGameObjectCoordinates jsonGameObjectCoordinates = new JsonGameObjectCoordinates(1, gameObjectsCoordinates);
        String jsonString = gson.toJson(jsonGameObjectCoordinates);
        TextMessage textMessage = new TextMessage(jsonString);
        userGameObjectArrangementController.handle(textMessage);
        Cage cage = playingField.identifyCage(x, y);
        assertEquals(State.FULL, cage.getState());
        verify(userSender).sendMessage(eq(spyUser), any(JsonMessage.class));
    }

    @Test
    public void handle_whenGameObjectCoordinatesAreNotValid_verifySendMessageToUser() {
        int x = 10;
        char y = 'z';
        List<List<CoordinatesCouple>> gameObjectsCoordinates = new ArrayList<>();
        CoordinatesCouple coordinatesCouple = new CoordinatesCouple(x, y);
        List<CoordinatesCouple> gameObjectCoordinatesBySizeOne = new ArrayList<>();
        gameObjectCoordinatesBySizeOne.add(coordinatesCouple);
        gameObjectsCoordinates.add(gameObjectCoordinatesBySizeOne);
        JsonGameObjectCoordinates jsonGameObjectCoordinates = new JsonGameObjectCoordinates(1, gameObjectsCoordinates);
        String jsonString = gson.toJson(jsonGameObjectCoordinates);
        TextMessage textMessage = new TextMessage(jsonString);
        userGameObjectArrangementController.handle(textMessage);
        verify(userSender).sendMessage(eq(spyUser), any(JsonMessage.class));
    }

    @Test
    public void handle_whenGameObjectCoordinatesAreValidButGameObjectSizeIsNotValid_returnPlayingFieldCageStateAndVerifySendMessageToUser() {
        int x = 1;
        char y = 'a';
        List<List<CoordinatesCouple>> gameObjectsCoordinates = new ArrayList<>();
        CoordinatesCouple coordinatesCouple = new CoordinatesCouple(x, y);
        List<CoordinatesCouple> gameObjectCoordinatesBySizeOne = new ArrayList<>();
        gameObjectCoordinatesBySizeOne.add(coordinatesCouple);
        gameObjectsCoordinates.add(gameObjectCoordinatesBySizeOne);
        JsonGameObjectCoordinates jsonGameObjectCoordinates = new JsonGameObjectCoordinates(10, gameObjectsCoordinates);
        String jsonString = gson.toJson(jsonGameObjectCoordinates);
        TextMessage textMessage = new TextMessage(jsonString);
        userGameObjectArrangementController.handle(textMessage);
        Cage cage = playingField.identifyCage(x, y);
        assertEquals(State.FREE, cage.getState());
        verify(userSender).sendMessage(eq(spyUser), any(JsonMessage.class));
    }
}