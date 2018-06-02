package com.seebattleserver.application.controller;

import com.seebattleserver.application.command.*;
import com.seebattleserver.application.user.User;
import com.seebattleserver.application.message.Message;
import com.seebattleserver.application.user.UserStatus;
import com.seebattleserver.service.sender.UserSender;

public class CommandController implements Controller {

    private final String[] commands = {"help", "list", "request"};

    private User user;
    private UserSender userSender;
    private CommandFactory commandFactory;

    public CommandController(User user, UserSender userSender, CommandFactory commandFactory) {
       this.user = user;
       this.userSender = userSender;
       this.commandFactory = commandFactory;
    }

    @Override
    public void handle(String message) {
        if (isRightCommand(message)) {
            handleCommand(message);
        } else {
            Command helpCommand = new HelpCommand();
            userSender.sendMessage(user, new Message (helpCommand.execute()));
        }
    }

    private boolean isRightCommand(String command) {
        for (int i = 0; i < commands.length; i++) {
            if (commands[i].equals(command)) {
                return true;
            }
        }
        return false;
    }

    private void handleCommand(String command) {
        switch (command) {
            case "help":
                Command helpCommand = commandFactory.createHelpCommand();
                userSender.sendMessage(user, new Message(helpCommand.execute()));
                break;
            case "list":
                Command listCommand = commandFactory.createPlayerListCommand();
                userSender.sendMessage(user, new Message(listCommand.execute()));
                break;
            case "request":
                Command requestCommand = commandFactory.createPlayerInvitationCommand();
                userSender.sendMessage(user, new Message(requestCommand.execute()));
                user.setUserStatus(UserStatus.REQUESTING_OPPONENT);
                break;
            default:
                throw new IllegalArgumentException("Данного запроса не существует");
        }
    }
}