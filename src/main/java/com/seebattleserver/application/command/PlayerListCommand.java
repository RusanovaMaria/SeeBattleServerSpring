package com.seebattleserver.application.command;

import com.seebattleserver.application.user.User;
import com.seebattleserver.application.user.UserRegistry;

import java.util.List;

public class PlayerListCommand implements Command {

    private UserRegistry userRegistry;

    public PlayerListCommand(UserRegistry userRegistry) {
        this.userRegistry = userRegistry;
    }

    @Override
    public String execute() {
        List<User> users = userRegistry.getUsers();
        String line = "";
        for (User user : users)
            line += user.getUsername() + "\n";
        return line;
    }

    private void writeOpponentList() {

    }

}
