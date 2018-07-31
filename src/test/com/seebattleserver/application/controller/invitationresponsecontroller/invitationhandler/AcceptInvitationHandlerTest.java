package com.seebattleserver.application.controller.invitationresponsecontroller.invitationhandler;

import com.seebattleserver.application.json.jsonmessage.JsonMessage;
import com.seebattleserver.application.user.User;
import com.seebattleserver.application.user.UserStatus;
import com.seebattleserver.service.sender.UserSender;
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

public class AcceptInvitationHandlerTest {
    private AcceptInvitationHandler acceptInvitationHandler;
    private User user;
    private User userOpponent;

    @Mock
    private UserSender userSender;

    @Before
    public void setUp() {
        initMocks(this);
        user = new User();
        userOpponent = new User();
        user.setUserOpponent(userOpponent);
        userOpponent.setUserOpponent(user);
        acceptInvitationHandler = new AcceptInvitationHandler(user, userSender);
    }

    @Test
    public void handleAnswer_whenUserOpponentNotInGame_returnUserAndUserOpponentStatusesAndVerifySendMessagesToUserAndUserOpponent() {
        acceptInvitationHandler.handleAnswer();
        assertEquals(UserStatus.READY_FOR_GAME, user.getUserStatus());
        assertEquals(UserStatus.READY_FOR_GAME, userOpponent.getUserStatus());
        verify(userSender).sendMessage(eq(user), any(JsonMessage.class));
        verify(userSender, times(2)).sendMessage(eq(userOpponent), any(JsonMessage.class));
    }

    @Test
    public void handleAnswer_whenUserOpponentInGame_returnUserStatusAndVerifySendMessageToUser() {
        userOpponent.setUserStatus(UserStatus.IN_GAME);
        acceptInvitationHandler.handleAnswer();
        assertEquals(UserStatus.FREE, user.getUserStatus());
        verify(userSender).sendMessage(eq(user), any(JsonMessage.class));
        verify(userSender).sendMessage(eq(user), any(JsonMessage.class));
    }
}