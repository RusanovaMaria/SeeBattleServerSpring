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
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class NotAcceptInvitationHandlerTest {
    private NotAcceptInvitationHandler notAcceptInvitationHandler;
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
        notAcceptInvitationHandler = new NotAcceptInvitationHandler(user, userSender);
    }

    @Test
    public void handleAnswer_returnUserAndUseOpponentStatusesAndOpponentsAndVerifySendMessagesToUserAndUserOpponent() {
        notAcceptInvitationHandler.handleAnswer();
        assertEquals(UserStatus.FREE, user.getUserStatus());
        assertEquals(UserStatus.FREE, userOpponent.getUserStatus());
        assertNull(user.getUserOpponent());
        assertNull(userOpponent.getUserOpponent());
        verify(userSender).sendMessage(eq(user), any(JsonMessage.class));
        verify(userSender).sendMessage(eq(userOpponent), any(JsonMessage.class));
    }
}