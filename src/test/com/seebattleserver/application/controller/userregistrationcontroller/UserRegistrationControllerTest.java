package com.seebattleserver.application.controller.userregistrationcontroller;

import com.google.gson.Gson;
import com.seebattleserver.application.json.jsonmessage.JsonMessage;
import com.seebattleserver.application.user.User;
import com.seebattleserver.application.user.UserRegistry;
import com.seebattleserver.application.user.UserStatus;
import com.seebattleserver.service.sender.UserSender;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.web.socket.TextMessage;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class UserRegistrationControllerTest {
    private UserRegistrationController userRegistrationController;
    private User user;
    private Gson gson;

    @Mock
    private UserRegistry userRegistry;

    @Mock
    private UserSender userSender;

    @Before
    public void setUp() {
        initMocks(this);
        user = new User();
        gson = new Gson();
        userRegistrationController = new UserRegistrationController(user, userRegistry, userSender);
    }

    @Test
    public void handle_whenUserEntersNameWhichDoNotExistInUserRegistry_returnUserStatusAndVerifySendMessageToUser() {
        String name = "User";
        JsonMessage jsonMessage = new JsonMessage(name);
        String jsonString = gson.toJson(jsonMessage);
        TextMessage textMessage = new TextMessage(jsonString);
        userRegistrationController.handle(textMessage);
        assertEquals(UserStatus.FREE, user.getUserStatus());
        verify(userSender).sendMessage(eq(user), any(JsonMessage.class));
    }

    @Test
    public void handle_whenUserEntersNameWhichExistsInUserRegistry_returnUserStatusAndVerifySendMessageToUser() {
        String name = "User";
        JsonMessage jsonMessage = new JsonMessage(name);
        String jsonString = gson.toJson(jsonMessage);
        TextMessage textMessage = new TextMessage(jsonString);
        when(userRegistry.getUserByName(name)).thenReturn(new User());
        userRegistrationController.handle(textMessage);
        assertEquals(UserStatus.REGISTERING, user.getUserStatus());
        verify(userSender).sendMessage(eq(user), any(JsonMessage.class));
    }
}