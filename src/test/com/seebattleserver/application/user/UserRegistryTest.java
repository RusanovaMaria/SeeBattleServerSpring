package com.seebattleserver.application.user;

import junit.framework.TestCase;
import org.junit.Before;
import org.mockito.Mock;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class UserRegistryTest extends TestCase {

    private UserRegistry userRegistry;

    @Mock
    private User user;

    @Before
    public void setUp() {
        initMocks(this);
        userRegistry = new UserRegistry();
    }

    public void testContains_whenUserInUserRegistry_returnTrue() {
        userRegistry.add(user);
        boolean result = userRegistry.contains(user);
        assertTrue(result);
    }

    public void testContains_whenUserNotInUserRegistry_returnFalse() {
        boolean result = userRegistry.contains(user);
        assertFalse(result);
    }

    public void testGetUserByName_whenValidName_returnUser() {
        final String NAME = "Mary";
        userRegistry.add(user);
        when(user.getUsername()).thenReturn(NAME);
        User result = userRegistry.getUserByName(NAME);
        assertEquals(user, result);
    }

    public void testGetUserByName_whenNotValidName_returnIllegalArgumentException() {
        final String VALID_NAME = "Mary";
        final String NOT_VALID_NAME = "Ann";
        userRegistry.add(user);
        when(user.getUsername()).thenReturn(VALID_NAME);
        UserRegistry spy = spy(userRegistry);
        doThrow(new IllegalArgumentException()).when(spy).getUserByName(NOT_VALID_NAME);
    }
}