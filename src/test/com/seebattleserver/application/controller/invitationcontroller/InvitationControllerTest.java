package com.seebattleserver.application.controller.invitationcontroller;

import com.google.gson.Gson;
import com.seebattleserver.application.json.jsonmessage.JsonMessage;
import com.seebattleserver.application.user.User;
import com.seebattleserver.application.user.UserRegistry;
import com.seebattleserver.application.user.UserStatus;
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

public class InvitationControllerTest {
    private InvitationController invitationController;
    private User user;
    private User userOpponent;
    private Gson gson;

    @Mock
    private UserRegistry userRegistry;

    @Mock
    private UserSender userSender;

    @Before
    public void setUp() {
        initMocks(this);
        user = new User();
        userOpponent = new User();
        user.setUserOpponent(userOpponent);
        gson = new Gson();
        invitationController = new InvitationController(user, userRegistry, userSender);
    }

    @Test
    public void handle_whenUserOpponentStatusIsFree_returnUserOpponentStatusAndVerifySendMessageToUserAndUserOpponent() {
        String userOpponentName = "name";
        userOpponent.setUserStatus(UserStatus.FREE);
        User spyUser = spy(user);
        when(userRegistry.getUserByName(userOpponentName)).thenReturn(spyUser);
        JsonMessage jsonMessage = new JsonMessage(userOpponentName);
        String jsonString = gson.toJson(jsonMessage);
        TextMessage textMessage = new TextMessage(jsonString);
        invitationController.handle(textMessage);
        assertEquals(UserStatus.INVITED, userOpponent.getUserStatus());
        verify(userSender).sendMessage(eq(user), any(JsonMessage.class));
        verify(userSender).sendMessage(eq(userOpponent), any(JsonMessage.class));
    }

    @Test
    public void handle_whenUserOpponentStatusIsNotFree_returnUserOpponentStatusAndVerifySendMessageToUser() {
        String userOpponentName = "name";
        userOpponent.setUserStatus(UserStatus.READY_FOR_GAME);
        User spyUser = spy(user);
        when(userRegistry.getUserByName(userOpponentName)).thenReturn(spyUser);
        JsonMessage jsonMessage = new JsonMessage(userOpponentName);
        String jsonString = gson.toJson(jsonMessage);
        TextMessage textMessage = new TextMessage(jsonString);
        invitationController.handle(textMessage);
        assertEquals(UserStatus.READY_FOR_GAME, userOpponent.getUserStatus());
        verify(userSender).sendMessage(eq(user), any(JsonMessage.class));
    }
}