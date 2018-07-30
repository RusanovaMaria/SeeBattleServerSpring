package com.seebattleserver.application.user;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserRegistryTest {
    private UserRegistry userRegistry;

    @Before
    public void setUp() {
        userRegistry = new UserRegistry();
    }

    @Test
    public void getUserByName_whenUserExistsInUserRegistry_returnUser() {
        String name = "Ann";
        User user = new User();
        user.setUsername(name);
        userRegistry.add(user);
        User result = userRegistry.getUserByName(name);
        assertEquals(user, result);
    }


    @Test
    public void getUserByName_whenUserDoesNotExistInUserRegistry_returnNull() {
        String name = "Ann";
        User user = new User();
        user.setUsername(name);
        userRegistry.add(user);
        User result = userRegistry.getUserByName("Mary");
        assertNull(result);
    }

    @Test
    public void getUserByName_whenUserRegistryIsEmpty_returnNull() {
        User result = userRegistry.getUserByName("Mary");
        assertNull(result);
    }
}