package com.seebattleserver.application.controller.invitationresponsecontroller;

import com.google.gson.Gson;
import com.seebattleserver.application.json.jsonmessage.JsonMessage;
import com.seebattleserver.application.user.User;
import com.seebattleserver.application.user.UserStatus;
import com.seebattleserver.service.sender.UserSender;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.web.socket.TextMessage;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class InvitationResponseControllerTest {
    private InvitationResponseController invitationResponseController;
    private User user;
    private User userOpponent;
    private Gson gson;

    @Mock
    private UserSender userSender;

    @Before
    public void setUp() {
        initMocks(this);
        user = new User();
        userOpponent = new User();
        user.setUserOpponent(userOpponent);
        userOpponent.setUserOpponent(user);
        gson = new Gson();
        invitationResponseController = new InvitationResponseController(user, userSender);
    }

    @Test
    public void handle_whenUserAcceptsInvitation_returnUserAndUserOpponentStatuses() {
        String positiveAnswer = "yes";
        JsonMessage jsonMessage = new JsonMessage(positiveAnswer);
        String jsonString = gson.toJson(jsonMessage);
        TextMessage textMessage = new TextMessage(jsonString);
        invitationResponseController.handle(textMessage);
        assertEquals(UserStatus.READY_FOR_GAME, user.getUserStatus());
        assertEquals(UserStatus.READY_FOR_GAME, userOpponent.getUserStatus());
    }

    @Test
    public void handle_whenUserDoAcceptInvitation_returnUserAndUserOpponentStatuses() {
        String positiveAnswer = "no";
        JsonMessage jsonMessage = new JsonMessage(positiveAnswer);
        String jsonString = gson.toJson(jsonMessage);
        TextMessage textMessage = new TextMessage(jsonString);
        invitationResponseController.handle(textMessage);
        assertEquals(UserStatus.FREE, user.getUserStatus());
        assertEquals(UserStatus.FREE, userOpponent.getUserStatus());
    }

    @Test
    public void handle_whenUserGivesNotValidAnswer_verifySendMessageToUser() {
        String positiveAnswer = "notValidAnswer";
        JsonMessage jsonMessage = new JsonMessage(positiveAnswer);
        String jsonString = gson.toJson(jsonMessage);
        TextMessage textMessage = new TextMessage(jsonString);
        invitationResponseController.handle(textMessage);
        verify(userSender).sendMessage(eq(user), any(JsonMessage.class));
    }
}