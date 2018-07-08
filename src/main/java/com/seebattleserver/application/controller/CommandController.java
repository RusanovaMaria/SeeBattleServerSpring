package com.seebattleserver.application.controller;

import com.google.gson.Gson;
import com.seebattleserver.application.command.*;
import com.seebattleserver.application.command.commandlist.CommandList;
import com.seebattleserver.application.user.User;
import com.seebattleserver.application.message.Message;
import com.seebattleserver.application.user.UserStatus;
import com.seebattleserver.service.sender.UserSender;
import org.springframework.web.socket.TextMessage;

public class CommandController implements Controller {
    private User user;
    private CommandList commandList;
    private Gson gson;
    private UserSender userSender;

    public CommandController(User user, CommandList commandList, Gson gson,
                             UserSender userSender) {
        this.user = user;
        this.commandList = commandList;
        this.gson = gson;
        this.userSender = userSender;
    }

    @Override
    public void handle(String commandWord) {
        Command command = commandList.getCommand(commandWord);
        if (command instanceof PlayerInvitationCommand) {
            user.setUserStatus(UserStatus.REQUESTING_OPPONENT);
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