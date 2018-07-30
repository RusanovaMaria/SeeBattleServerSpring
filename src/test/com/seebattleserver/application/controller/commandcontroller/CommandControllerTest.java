package com.seebattleserver.application.controller.commandcontroller;

import com.google.gson.Gson;
import com.seebattleserver.application.controller.Controller;
import com.seebattleserver.application.json.jsonmessage.JsonMessage;
import com.seebattleserver.application.user.User;
import com.seebattleserver.application.user.UserRegistry;
import com.seebattleserver.service.sender.UserSender;
import com.seebattleserver.service.sender.WebSocketUserSender;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.web.socket.TextMessage;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class CommandControllerTest {
    private CommandController controller;
    private User user;
    private CommandList commandList;
    private Gson gson;

    @Mock
    private WebSocketUserSender userSender;

    @Mock
    private UserRegistry userRegistry;

    @Before
    public void setUp() {
        initMocks(this);
        user = mock(User.class);
        CommandList list = new CommandList(userRegistry);
        commandList = spy(list);
        gson = new Gson();
        controller = new CommandController(user, commandList, userSender);
    }

    @Test
    public void handle_whenCommandIsCorrect_verifyGetCommandFromCommandListAndSendMessageToUser() {
        JsonMessage jsonMessage = new JsonMessage("help");
        String jsonString = gson.toJson(jsonMessage);
        TextMessage textMessage = new TextMessage(jsonString);
        controller.handle(textMessage);
        verify(commandList).getCommand(jsonMessage.getContent());
        verify(userSender).sendMessage(eq(user), any(JsonMessage.class));
    }

    @Test
    public void handle_whenCommandIsNotCorrect_verifyGetDefaultCommandFromCommandListAndSendMessageToUser() {
        JsonMessage jsonMessage = new JsonMessage("notCorrectCommand");
        String jsonString = gson.toJson(jsonMessage);
        TextMessage textMessage = new TextMessage(jsonString);
        controller.handle(textMessage);
        verify(commandList).getDefaultCommand();
        verify(userSender).sendMessage(eq(user), any(JsonMessage.class));
    }

    @Test
    public void handle_whenCommandIsNull_verifyGetDefaultCommandFromCommandListAndSendMessageToUser() {
        JsonMessage jsonMessage = new JsonMessage(null);
        String jsonString = gson.toJson(jsonMessage);
        TextMessage textMessage = new TextMessage(jsonString);
        controller.handle(textMessage);
        verify(commandList).getDefaultCommand();
        verify(userSender).sendMessage(eq(user), any(JsonMessage.class));
    }
}