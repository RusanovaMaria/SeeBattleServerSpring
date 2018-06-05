package com.seebattleserver.application.invitation;

import com.seebattleserver.application.gameregistry.GameRegistry;
import com.seebattleserver.application.user.User;
import com.seebattleserver.service.sender.UserSender;
import junit.framework.TestCase;

import static org.mockito.Mockito.mock;

public class AcceptInvitationTest extends TestCase {

    public void testHandleAnswer() {
        User user = new User(null);
        User opponent = new User(null);
        user.setOpponent(opponent);
        UserSender userSender = mock(UserSender.class);
        GameRegistry gameRegistry = mock(GameRegistry.class);
        Invitation invitation = new AcceptInvitation(user, userSender, gameRegistry);
        invitation.handleAnswer();
    }
}