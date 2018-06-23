package com.seebattleserver.application.command;

import com.seebattleserver.application.user.UserRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommandFactory {

    private UserRegistry userRegistry;

    @Autowired
    public CommandFactory(UserRegistry userRegistry) {
        this.userRegistry = userRegistry;
    }

    public HelpCommand createHelpCommand() {
        return new HelpCommand();
    }

    public PlayerInvitationCommand createPlayerInvitationCommand() {
        return new PlayerInvitationCommand();
    }

    public PlayerListCommand createPlayerListCommand() {
        return new PlayerListCommand(userRegistry);
    }
}
