package com.seebattleserver.application.controller;

import com.seebattleserver.application.gameregistry.GameRegistry;
import com.seebattleserver.application.message.Message;
import com.seebattleserver.application.user.User;
import com.seebattleserver.service.sender.UserSender;
import junit.framework.TestCase;
import org.junit.Before;
import org.mockito.Mock;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class InvitationControllerTest extends TestCase {

    private final String POSITIVE_ANSWER = "yes";

    private final String NEGATIVE_ANSWER = "no";

    private final String NOT_VALID_ANSWER = "sdffvf";

    private Controller controller;

    @Mock
    private User user;

    @Mock
    private User opponent = new User(null);

    @Mock
    private UserSender userSender;

    @Mock
    private GameRegistry gameRegistry;

    @Before
    public void setUp() {
        initMocks(this);
        controller = new InvitationController(user, userSender, gameRegistry);
    }

    public void testHandle_whenPositiveAnswer_returnVerificationForAcceptInvitationHandleAnswer() {
        when(user.getOpponent()).thenReturn(opponent);
        controller.handle(POSITIVE_ANSWER);
        verify(userSender, times(2)).sendMessage(eq(opponent), any(Message.class));
    }

    public void testHandle_whenNegativeAnswer_returnVerificationForNotAcceptInvitationHandleAnswer() {
        when(user.getOpponent()).thenReturn(opponent);
        controller.handle(NEGATIVE_ANSWER);
        verify(userSender, times(1)).sendMessage(eq(opponent), any(Message.class));
    }

    public void testHandle_whenNotValidAnswer_returnIllegalArgumentException() {
        Controller spy = spy(controller);
        controller.handle(NOT_VALID_ANSWER);
        verify(userSender, times(1)).sendMessage(eq(user), any(Message.class));
    }
}