package com.seebattleserver.application.controller;

import com.seebattleserver.application.user.User;
import com.seebattleserver.application.user.UserRegistry;
import com.seebattleserver.application.user.UserStatus;
import com.seebattleserver.service.sender.UserSender;
import junit.framework.TestCase;
import org.junit.Before;
import org.mockito.Mock;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class RequestOpponentControllerTest extends TestCase {

    private Controller controller;

    @Mock
    private User user;

    @Mock
    private User opponent;

    @Mock
    private UserRegistry userRegistry;

    @Mock
    private UserSender userSender;

    @Before
    public void setUp() {
        initMocks(this);
        controller = new RequestOpponentController(user, userRegistry, userSender);
    }

    public void testHandle_whenOpponentIsFree_returnVerificationForChangeUsersStatuses() {
        when(userRegistry.getUserByName(anyString())).thenReturn(opponent);
        when(opponent.getUserStatus()).thenReturn(UserStatus.FREE);
        controller.handle(anyString());
        verify(user).setUserStatus(UserStatus.INVITING);
        verify(opponent).setUserStatus(UserStatus.INVITED);
    }

    public void testHandle_whenOpponentIsNotFree_returnVerificationForChangeUsersStatuses() {
        when(userRegistry.getUserByName(anyString())).thenReturn(opponent);
        when(opponent.getUserStatus()).thenReturn(UserStatus.IN_GAME);
        controller.handle(anyString());
        verify(user, never()).setUserStatus(UserStatus.INVITING);
        verify(opponent, never()).setUserStatus(UserStatus.INVITED);
    }
}