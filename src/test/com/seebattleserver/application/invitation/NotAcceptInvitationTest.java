package com.seebattleserver.application.invitation;

import com.seebattleserver.application.user.User;
import com.seebattleserver.service.sender.UserSender;
import junit.framework.TestCase;

import static org.mockito.Mockito.mock;

public class NotAcceptInvitationTest extends TestCase {

    public void testHandleAnswer() {
            User user = new User(null);
            User opponent = new User(null);
            user.setOpponent(opponent);
            UserSender userSender = mock(UserSender.class);
            Invitation invitation = new NotAcceptInvitation(user, userSender);
            invitation.handleAnswer();
    }
}