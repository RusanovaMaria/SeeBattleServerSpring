package com.seebattleserver.application.command;

import com.seebattleserver.application.user.User;

public class PlayerInvitationCommand implements Command {

    private User user;

    public PlayerInvitationCommand(User user) {
        this.user = user;
    }

    @Override
    public String execute() {
        return "Введите имя соперника";
    }
}
