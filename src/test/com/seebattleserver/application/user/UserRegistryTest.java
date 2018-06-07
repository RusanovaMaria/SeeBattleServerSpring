package com.seebattleserver.application.user;

import junit.framework.TestCase;
import org.mockito.Mockito;

import static org.mockito.Mockito.doThrow;

public class UserRegistryTest extends TestCase {

    public void testHandleAnswer_whenUserNameIsValid_returnUser() {
        UserRegistry userRegistry = new UserRegistry();
        User user = new User("Mary");
        userRegistry.add(user);
        User result = userRegistry.getUserByName("Mary");
        assertEquals(user, result);
    }

    public void testHandleAnswer_whenUserNameIsNotValid_returnUser() {
        UserRegistry userRegistry = new UserRegistry();
        UserRegistry spy = Mockito.spy(userRegistry);
        User user = new User("Mary");
        userRegistry.add(user);
        doThrow(new IllegalArgumentException()).when(spy).getUserByName("Michel");
    }
}