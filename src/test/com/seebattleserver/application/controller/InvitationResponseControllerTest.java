package com.seebattleserver.application.controller;

import com.google.gson.Gson;
import com.seebattleserver.application.gameregistry.GameRegistry;
import com.seebattleserver.application.message.Message;
import com.seebattleserver.application.user.User;
import com.seebattleserver.service.sender.UserSender;
import junit.framework.TestCase;
import org.junit.Before;
import org.mockito.Mock;
import org.springframework.web.socket.TextMessage;

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

    private Gson gson;

    @Before
    public void setUp() {
        initMocks(this);
        gson = new Gson();
        controller = new InvitationController(user, userSender, gameRegistry, gson);
    }

    public void testHandle_whenPositiveAnswer_returnVerificationForAcceptInvitationHandleAnswer() {
        when(user.getOpponent()).thenReturn(opponent);
        TextMessage text = new TextMessage(POSITIVE_ANSWER);
        controller.handle(text);
        verify(userSender, times(2)).sendMessage(eq(opponent), any(Message.class));
    }

    public void testHandle_whenNegativeAnswer_returnVerificationForNotAcceptInvitationHandleAnswer() {
        when(user.getOpponent()).thenReturn(opponent);
        TextMessage text = new TextMessage(NEGATIVE_ANSWER);
        controller.handle(text);
        verify(userSender, times(1)).sendMessage(eq(opponent), any(Message.class));
    }

    public void testHandle_whenNotValidAnswer_returnIllegalArgumentException() {
        Controller spy = spy(controller);
        TextMessage text = new TextMessage(NOT_VALID_ANSWER);
        controller.handle(text);
        verify(userSender, times(1)).sendMessage(eq(user), any(Message.class));
    }
}