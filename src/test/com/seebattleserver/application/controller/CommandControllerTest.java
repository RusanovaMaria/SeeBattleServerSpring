package com.seebattleserver.application.controller;

import com.google.gson.Gson;
import com.seebattleserver.application.command.*;
import com.seebattleserver.application.message.Message;
import com.seebattleserver.application.user.User;
import com.seebattleserver.application.user.UserRegistry;
import com.seebattleserver.service.sender.UserSender;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.web.socket.TextMessage;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class CommandControllerTest extends TestCase {

    private Controller controller;

    @Mock
    private  User user;

    @Mock
    private UserSender userSender;

    @Mock
    private CommandFactory commandFactory;

    private Gson gson;

    @Before
    public void setUp() {
        initMocks(this);
        gson = new Gson();
        controller = new CommandController(user, userSender, commandFactory, gson);
    }

    @Test
    public void testHandle_whenHelpCommand_returnVerifyCommandFactoryCreateCommand() {
        String HELP_COMMAND = "help";
        TextMessage text = new TextMessage(HELP_COMMAND);
        Command command = new HelpCommand();
        when(commandFactory.createHelpCommand()).thenReturn(mock(HelpCommand.class));
        controller.handle(text);
        verify(commandFactory).createHelpCommand();
        verify(userSender).sendMessage(eq(user), any(Message.class));
    }

    @Test
    public void testHandle_whenPlayerListCommand_returnVerifyCommandFactoryCreatePlayerListCommand() {
        String LIST_COMMAND = "list";
        TextMessage text = new TextMessage(LIST_COMMAND);
        UserRegistry userRegistry = mock(UserRegistry.class);
        Command command = new PlayerListCommand(userRegistry);
        when(commandFactory.createPlayerListCommand()).thenReturn(mock(PlayerListCommand.class));
        controller.handle(text);
        verify(commandFactory).createPlayerListCommand();
        verify(userSender).sendMessage(eq(user), any(Message.class));
    }

    @Test
    public void testHandle_whenPlayerInvitationCommand_returnVerificationForCommandFactoryCreatePlayerInvitationCommand() {
        String REQUEST_COMMAND = "request";
        TextMessage text = new TextMessage(REQUEST_COMMAND);
        UserRegistry userRegistry = mock(UserRegistry.class);
        Command command = new PlayerInvitationCommand();
        when(commandFactory.createPlayerInvitationCommand()).thenReturn(mock(PlayerInvitationCommand.class));
        controller.handle(text);
        verify(commandFactory).createPlayerInvitationCommand();
        verify(userSender).sendMessage(eq(user), any(Message.class));
    }

    @Test
    public void testHandle_whenNotValidCommand_returnVerificationForCommandFactoryCreateDefaultCommand() {
        String WRONG_COMMAND = "hgjgg";
        TextMessage text = new TextMessage(WRONG_COMMAND);
        Command command = new HelpCommand();
        when(commandFactory.createHelpCommand()).thenReturn(mock(HelpCommand.class));
        controller.handle(text);
        verify(commandFactory).createHelpCommand();
        verify(userSender).sendMessage(eq(user), any(Message.class));
    }

    @Test
    public void testHandle_whenNullCommand_returnVerificationForCommandFactoryCreateHelpCommand() {
        Command command = new HelpCommand();
        when(commandFactory.createHelpCommand()).thenReturn(mock(HelpCommand.class));
        controller.handle(null);
        verify(commandFactory).createHelpCommand();
        verify(userSender).sendMessage(eq(user), any(Message.class));
    }
}