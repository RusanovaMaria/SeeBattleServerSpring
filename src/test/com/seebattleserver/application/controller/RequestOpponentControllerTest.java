package com.seebattleserver.application.controller;

import com.seebattleserver.application.user.User;
import com.seebattleserver.application.user.UserRegistry;
import com.seebattleserver.service.sender.UserSender;
import junit.framework.TestCase;
import org.mockito.Mockito;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

public class RequestOpponentControllerTest extends TestCase {

    public void testHandle_whenValidUserName() {
        User user = new User(null);
        User opponent = new User("Mary");
        UserRegistry userRegistry = new UserRegistry();
        userRegistry.add(opponent);
        UserSender userSender = mock(UserSender.class);
        Controller requestOpponentController = new RequestOpponentController(user, userRegistry, userSender);
        requestOpponentController.handle("Mary");
    }

    public void testHandle_whenNotValidUserName() {
        User user = new User(null);
        User opponent = new User("Mary");
        UserRegistry userRegistry = new UserRegistry();
        userRegistry.add(opponent);
        UserSender userSender = mock(UserSender.class);
        Controller requestOpponentController = new RequestOpponentController(user, userRegistry, userSender);
        Controller spy = Mockito.spy(requestOpponentController);
        doThrow(new IllegalArgumentException()).when(spy).handle("Michel");
    }
}