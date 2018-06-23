package com.seebattleserver.application.invitation;

import com.seebattleserver.application.gameregistry.GameRegistry;
import com.seebattleserver.application.user.User;
import com.seebattleserver.application.user.UserStatus;
import com.seebattleserver.domain.game.ClassicGame;
import com.seebattleserver.service.sender.UserSender;
import junit.framework.TestCase;
import org.junit.Before;
import org.mockito.Mock;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class AcceptInvitationTest extends TestCase {

    private Invitation invitation;

    @Mock
    private User user;

    @Mock
    private User opponent;

    @Mock
    private UserSender userSender;

    @Mock
    private GameRegistry gameRegistry;

    @Before
    public void setUp() {
        initMocks(this);
        invitation = new AcceptInvitation(user, userSender, gameRegistry);
    }

    public void testHandleAnswer_returnVerificationForChangeUserStatusesAndUsersPurToGameRegistry() {
        when(user.getOpponent()).thenReturn(opponent);
        invitation.handleAnswer();
        verify(user).setUserStatus(UserStatus.IN_GAME);
        verify(opponent).setUserStatus(UserStatus.IN_GAME_MOVE);
        verify(gameRegistry).put(eq(user), any(ClassicGame.class));
        verify(gameRegistry).put(eq(opponent), any(ClassicGame.class));
    }
}