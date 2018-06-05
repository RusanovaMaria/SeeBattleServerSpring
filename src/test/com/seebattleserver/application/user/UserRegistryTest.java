package com.seebattleserver.application.user;

import com.seebattleserver.application.gameregistry.GameRegistry;
import com.seebattleserver.application.invitation.AcceptInvitation;
import com.seebattleserver.application.invitation.Invitation;
import com.seebattleserver.service.sender.UserSender;
import junit.framework.TestCase;
import org.mockito.Mockito;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

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