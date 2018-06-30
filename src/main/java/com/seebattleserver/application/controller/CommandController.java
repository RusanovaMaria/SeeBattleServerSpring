package com.seebattleserver.application.controller;

import com.google.gson.Gson;
import com.seebattleserver.application.command.*;
import com.seebattleserver.application.user.User;
import com.seebattleserver.application.message.Message;
import com.seebattleserver.application.user.UserStatus;
import com.seebattleserver.service.sender.UserSender;
import org.springframework.web.socket.TextMessage;

public class CommandController implements Controller {

    private static final String LIST_COMMAND = "list";
    private static final String HELP_COMMAND = "help";
    private static final String REQUEST_OPPONENT_COMMAND = "request";
    private static final String[] commands = {HELP_COMMAND, LIST_COMMAND, REQUEST_OPPONENT_COMMAND};
    private static final String DEFAULT_COMMAND = HELP_COMMAND;

    private User user;
    private UserSender userSender;
    private CommandFactory commandFactory;
    private Gson gson;

    public CommandController(User user, UserSender userSender, CommandFactory commandFactory, Gson gson) {
        this.user = user;
        this.userSender = userSender;
        this.commandFactory = commandFactory;
        this.gson = gson;
    }

    @Override
    public void handle(TextMessage text) {
        Message message = gson.fromJson(text.getPayload() ,Message.class);
        String content = message.getContent();
        Command command;
        if (isRightCommand(content)) {
            command = createCommand(content);
            if (content.equals(REQUEST_OPPONENT_COMMAND)) {
                user.setUserStatus(UserStatus.REQUESTING_OPPONENT);
            }
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
            case HELP_COMMAND:
                Command helpCommand = commandFactory.createHelpCommand();
                return helpCommand;
            case LIST_COMMAND:
                Command listCommand = commandFactory.createPlayerListCommand();
                return listCommand;
            case REQUEST_OPPONENT_COMMAND:
                Command requestCommand = commandFactory.createPlayerInvitationCommand();
                return requestCommand;
            default:
                throw new IllegalArgumentException("Данного запроса не существует");
        }
    }
}