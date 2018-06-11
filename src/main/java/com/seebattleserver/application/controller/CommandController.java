package com.seebattleserver.application.controller;

import com.seebattleserver.application.command.*;
import com.seebattleserver.application.user.User;
import com.seebattleserver.application.message.Message;
import com.seebattleserver.application.user.UserStatus;
import com.seebattleserver.service.sender.UserSender;

public class CommandController implements Controller {

    private final String[] commands = {"help", "list", "request"};
    private final String DEFAULT_COMMAND = "help";

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
        Command command;
        if (isRightCommand(message)) {
            command = createCommand(message);
        } else {
            command = createCommand(DEFAULT_COMMAND);
        }
        String answer = command.execute();
        userSender.sendMessage(user, new Message(answer));
    }

    private boolean isRightCommand(String command) {
        for (int i = 0; i < commands.length; i++) {
            if (commands[i].equals(command)) {
                return true;
            }
        }
        return false;
    }

    private Command createCommand(String command) {
        switch (command) {
            case "help":
                Command helpCommand = commandFactory.createHelpCommand();
                return helpCommand;
            case "list":
                Command listCommand = commandFactory.createPlayerListCommand();
                return listCommand;
            case "request":
                Command requestCommand = commandFactory.createPlayerInvitationCommand();
                return requestCommand;
            default:
                throw new IllegalArgumentException("Данного запроса не существует");
        }
    }
}