package com.seebattleserver.application.controller;

import com.seebattleserver.application.command.Command;
import com.seebattleserver.application.command.HelpCommand;
import com.seebattleserver.application.command.PlayerInvitationCommand;
import com.seebattleserver.application.command.PlayerListCommand;
import com.seebattleserver.application.user.User;
import com.seebattleserver.application.user.UserRegistry;
import com.seebattleserver.service.message.Message;
import com.seebattleserver.service.sender.UserSender;

public class CommandController implements Controller {

    private final String[] commands = {"help", "list", "request"};

    private User user;
    private UserSender userSender;
    private UserRegistry userRegistry;

    public CommandController(User user, UserSender userSender, UserRegistry userRegistry) {
       this.user = user;
       this.userSender = userSender;
       this.userRegistry = userRegistry;
    }

    @Override
    public void handle(String command) {
        if (isRightCommand(command)) {
            handleCommand(command);
        } else {
            Command helpCommand = new HelpCommand();
            helpCommand.execute();
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
                Command helpCommand = new HelpCommand();
                userSender.sendMessage(user, new Message(helpCommand.execute()));
                break;
            case "list":
                Command listCommand = new PlayerListCommand(userRegistry);
                userSender.sendMessage(user, new Message(listCommand.execute()));
                break;
            case "request":
                Command requestCommand = new PlayerInvitationCommand(user);
                userSender.sendMessage(user, new Message(requestCommand.execute()));
                break;
            default:
                throw new IllegalArgumentException("Данного запроса не существует");
        }
    }
}