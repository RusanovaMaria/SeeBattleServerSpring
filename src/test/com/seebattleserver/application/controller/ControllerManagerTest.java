package com.seebattleserver.application.controller;

import com.seebattleserver.application.user.User;
import com.seebattleserver.application.user.UserStatus;
import junit.framework.TestCase;
import org.junit.Before;
import org.mockito.Mock;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class ControllerManagerTest extends TestCase {

    private static final String TEST = "test";

    private ControllerManager controllerManager;

    private TextMessage test;

    @Mock
    private User user;

    @Mock
    private ControllerFactory controllerFactory;

    @Before
    public void setUp() {
        initMocks(this);
        controllerManager = new ControllerManager(controllerFactory);
        test = new TextMessage(TEST);
    }

    public void testHandle_whenUserStatusIsFree_returnVerificationForControllerFactoryCreateCommandController() {
        when(user.getUserStatus()).thenReturn(UserStatus.FREE);
        when(controllerFactory.createCommandController(user)).thenReturn(mock(CommandController.class));
        controllerManager.handle(user, test);
        verify(controllerFactory).createCommandController(user);
    }

    public void testHandle_whenUserStatusIsInvited_returnVerificationForControllerFactoryCreateInvitationController() {
        when(user.getUserStatus()).thenReturn(UserStatus.INVITED);
        when(controllerFactory.createInvitationController(user)).thenReturn(mock(InvitationController.class));
        controllerManager.handle(user, test);
        verify(controllerFactory).createInvitationController(user);
    }

    public void testHandle_whenUserStatusIsInviting_returnVerificationForControllerFactoryCreateCommandController() {
        when(user.getUserStatus()).thenReturn(UserStatus.INVITING);
        when(controllerFactory.createCommandController(user)).thenReturn(mock(CommandController.class));
        controllerManager.handle(user, test);
        verify(controllerFactory).createCommandController(user);
    }

    public void testHandle_whenUserStatusIsInGame_returnVerificationForControllerFactoryCreateGameController() {
        when(user.getUserStatus()).thenReturn(UserStatus.IN_GAME);
        when(controllerFactory.createGameController(user)).thenReturn(mock(GameController.class));
        controllerManager.handle(user, test);
        verify(controllerFactory).createGameController(user);
    }

    public void testHandle_whenUserStatusIsInGameMove_returnVerificationForControllerFactoryCreateGameController() {
        when(user.getUserStatus()).thenReturn(UserStatus.IN_GAME_MOVE);
        when(controllerFactory.createGameController(user)).thenReturn(mock(GameController.class));
        controllerManager.handle(user, test);
        verify(controllerFactory).createGameController(user);
    }

    public void testHandle_whenUserStatusIsRequestingOpponent_returnVerificationForControllerFactoryCreateRequestOpponentController() {
        when(user.getUserStatus()).thenReturn(UserStatus.REQUESTING_OPPONENT);
        when(controllerFactory.createRequestOpponentController(user)).thenReturn(mock(RequestOpponentController.class));
        controllerManager.handle(user, test);
        verify(controllerFactory).createRequestOpponentController(user);
    }

    public void testHandle_whenUserStatusIsNull_returnIllegalStateException() {
        when(user.getUserStatus()).thenReturn(null);
        ControllerManager spy = spy(controllerManager);
        doThrow(new IllegalStateException()).when(spy).handle(eq(user), test);
    }
}
