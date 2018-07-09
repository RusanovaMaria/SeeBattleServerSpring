package com.seebattleserver.application.controller.commandcontroller;

import com.seebattleserver.application.command.*;
import com.seebattleserver.application.controller.Controller;
import com.seebattleserver.application.user.User;
import com.seebattleserver.application.message.Message;
import com.seebattleserver.application.user.UserStatus;
import com.seebattleserver.service.sender.UserSender;

public class CommandController implements Controller {
    private User user;
    private CommandList commandList;
    private UserSender userSender;

    public CommandController(User user, CommandList commandList,
                             UserSender userSender) {
        this.user = user;
        this.commandList = commandList;
        this.userSender = userSender;
    }

    @Override
    public void handle(String commandWord) {
        Command command = commandList.getCommand(commandWord);
        if (command instanceof PlayerInvitationCommand) {
            user.setUserStatus(UserStatus.INVITING);
        }
        if (isNull(command)) {
            command = commandList.getDefaultCommand();
        }
        sendCommandExecution(command);
    }

    private boolean isNull(Command command) {
        if (command == null) {
            return true;
        }
        return false;
    }

    private void sendCommandExecution(Command command) {
        userSender.sendMessage(user, new Message(command.execute()));
    }
}