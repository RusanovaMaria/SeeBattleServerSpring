package com.seebattleserver.application.invitation;

import com.seebattleserver.application.user.User;
import com.seebattleserver.application.user.UserStatus;
import com.seebattleserver.service.sender.UserSender;
import junit.framework.TestCase;
import org.junit.Before;
import org.mockito.Mock;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class NotAcceptInvitationTest extends TestCase {

    private Invitation invitation;

    @Mock
    private User user;

    @Mock
    private User opponent;

    @Mock
    private UserSender userSender;

    @Before
    public void setUp() {
        initMocks(this);
        invitation = new NotAcceptInvitation(user, userSender);
    }

    public void testHandleAnswer_returnVerificationForChangeUserStatusesInFeeAndResetUsersOpponents() {
        when(user.getOpponent()).thenReturn(opponent);
        invitation.handleAnswer();
        verify(user).setUserStatus(UserStatus.FREE);
        verify(user).setOpponent(null);
        verify(opponent).setUserStatus(UserStatus.FREE);
        verify(opponent).setOpponent(null);
    }
}