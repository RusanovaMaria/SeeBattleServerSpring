package com.seebattleserver.application.controllermanager;

import com.google.gson.Gson;
import com.seebattleserver.application.controller.commandcontroller.CommandList;
import com.seebattleserver.application.controllerfactory.ControllerFactory;
import com.seebattleserver.application.gameregistry.GameRegistry;
import com.seebattleserver.application.json.jsonmessage.JsonMessage;
import com.seebattleserver.application.user.User;
import com.seebattleserver.application.user.UserRegistry;
import com.seebattleserver.application.user.UserStatus;
import com.seebattleserver.service.sender.UserSender;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.web.socket.TextMessage;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class ControllerManagerTest {
    private ControllerManager controllerManager;
    private ControllerFactory controllerFactory;
    private CommandList commandList;
    private Gson gson;

    @Mock
    private GameRegistry gameRegistry;

    @Mock
    private UserRegistry userRegistry;

    @Mock
    private UserSender userSender;

    @Before
    public void setUp() {
        initMocks(this);
        gson = new Gson();
        CommandList list = new CommandList(userRegistry);
        commandList = spy(list);
        ControllerFactory factory = new ControllerFactory(gameRegistry, userRegistry, commandList, userSender);
        controllerFactory = spy(factory);
        controllerManager = new ControllerManager(controllerFactory);
    }

    @Test
    public void handle_whenUserStatusIsRegistering_verifyControllerFactoryCreateUserRegistrationControllerAndSendMessageToUser() {
        User user = new User();
        User spy = spy(user);
        JsonMessage jsonMessage = new JsonMessage("test");
        String jsonString = gson.toJson(jsonMessage);
        TextMessage textMessage = new TextMessage(jsonString);
        controllerManager.handle(spy, textMessage);
        verify(controllerFactory).createUserRegistrationController(spy);
        verify(userSender).sendMessage(eq(spy), any(JsonMessage.class));
    }

    @Test
    public void handle_whenUserStatusIsFree_verifyControllerFactoryCreateCommandControllerAndSendMessageToUser() {
        User user = new User();
        user.setUserStatus(UserStatus.FREE);
        User spy = spy(user);
        JsonMessage jsonMessage = new JsonMessage("test");
        String jsonString = gson.toJson(jsonMessage);
        TextMessage textMessage = new TextMessage(jsonString);
        controllerManager.handle(spy, textMessage);
        verify(controllerFactory).createCommandController(spy);
        verify(userSender).sendMessage(eq(spy), any(JsonMessage.class));
    }

    @Test
    public void handle_whenUserStatusIsInvited_verifyControllerFactoryCreateInvitationResponseControllerAndSendMessageToUser() {
        User user = new User();
        user.setUserStatus(UserStatus.INVITED);
        User spy = spy(user);
        JsonMessage jsonMessage = new JsonMessage("test");
        String jsonString = gson.toJson(jsonMessage);
        TextMessage textMessage = new TextMessage(jsonString);
        controllerManager.handle(spy, textMessage);
        verify(controllerFactory).createInvitationResponseController(spy);
        verify(userSender).sendMessage(eq(spy), any(JsonMessage.class));
    }

    @Test
    public void handle_whenUserStatusIsInviting_verifyControllerFactoryCreateInvitingControllerAndSendMessageToUser() {
        User user = new User();
        user.setUserStatus(UserStatus.INVITING);
        User spy = spy(user);
        JsonMessage jsonMessage = new JsonMessage("test");
        String jsonString = gson.toJson(jsonMessage);
        TextMessage textMessage = new TextMessage(jsonString);
        controllerManager.handle(spy, textMessage);
        verify(controllerFactory).createInvitationController(spy);
        verify(userSender).sendMessage(eq(spy), any(JsonMessage.class));
    }

    @Test
    public void handle_whenUserStatusIsReadyForGame_verifyControllerFactoryCreateGameObjectArrangementControllerAndSendMessageToUser() {
        User user = new User();
        user.setUserStatus(UserStatus.READY_FOR_GAME);
        User spy = spy(user);
        JsonMessage jsonMessage = new JsonMessage("test");
        String jsonString = gson.toJson(jsonMessage);
        TextMessage textMessage = new TextMessage(jsonString);
        controllerManager.handle(spy, textMessage);
        verify(controllerFactory).createGameObjectArrangementController(spy);
        verify(userSender).sendMessage(eq(spy), any(JsonMessage.class));
    }

    @Test
    public void handle_whenUserStatusIsInGame_verifyControllerFactoryCreateGameProcessControllerAndSendMessageToUser() {
        User user = new User();
        user.setUserStatus(UserStatus.IN_GAME);
        User spy = spy(user);
        JsonMessage jsonMessage = new JsonMessage("test");
        String jsonString = gson.toJson(jsonMessage);
        TextMessage textMessage = new TextMessage(jsonString);
        controllerManager.handle(spy, textMessage);
        verify(controllerFactory).createGameProcessController(spy);
        verify(userSender).sendMessage(eq(spy), any(JsonMessage.class));
    }

    @Test
    public void handle_whenUserStatusIsInGameMove_verifyControllerFactoryCreateGameProcessControllerAndSendMessageToUser() {
        User user = new User();
        user.setUserStatus(UserStatus.IN_GAME_MOVE);
        User spy = spy(user);
        JsonMessage jsonMessage = new JsonMessage("test");
        String jsonString = gson.toJson(jsonMessage);
        TextMessage textMessage = new TextMessage(jsonString);
        controllerManager.handle(spy, textMessage);
        verify(controllerFactory).createGameProcessController(spy);
        verify(userSender).sendMessage(eq(spy), any(JsonMessage.class));
    }

    @Test
    public void handle_whenUserStatusIsSetUpGameObjects_verifyControllerFactoryCreateUserGameObjectArrangementControllerAndSendMessageToUser() {
        User user = new User();
        user.setUserStatus(UserStatus.SET_UP_GAME_OBJECTS);
        User spy = spy(user);
        JsonMessage jsonMessage = new JsonMessage("test");
        String jsonString = gson.toJson(jsonMessage);
        TextMessage textMessage = new TextMessage(jsonString);
        controllerManager.handle(spy, textMessage);
        verify(controllerFactory).createUserGameObjectArrangementController(spy);
        verify(userSender).sendMessage(eq(spy), any(JsonMessage.class));
    }
}