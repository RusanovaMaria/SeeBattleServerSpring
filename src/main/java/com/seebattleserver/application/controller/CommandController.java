package com.seebattleserver.application.controller;

import com.google.gson.Gson;
import com.seebattleserver.application.command.*;
import com.seebattleserver.application.command.commandlist.CommandList;
import com.seebattleserver.application.user.User;
import com.seebattleserver.application.message.Message;
import com.seebattleserver.application.user.UserStatus;
import org.springframework.web.socket.TextMessage;

import java.util.ArrayList;
import java.util.List;

public class CommandController implements Controller {
    private User user;
    private CommandList commandList;
    private Gson gson;
    private List<Message> response;

    public CommandController(User user, CommandList commandList, Gson gson) {
        this.user = user;
        this.commandList = commandList;
        this.gson = gson;
        response = new ArrayList<>();
    }

    @Override
    public List<Message> handle(TextMessage text) {
        String commandWord = getMessage(text);
        Command command = commandList.getCommand(commandWord);
        if (command instanceof PlayerInvitationCommand) {
            user.setUserStatus(UserStatus.INVITING);
        }
        if (isNull(command)) {
            command = commandList.getDefaultCommand();
        }
        makeResponse(command);
        return response;
    }

    private String getMessage(TextMessage text) {
        Message message = gson.fromJson(text.getPayload(), Message.class);
        String commandWord = message.getContent().trim();
        return commandWord;
    }

    private boolean isNull(Command command) {
        if (command == null) {
            return true;
        }
        return false;
    }

    private void makeResponse(Command command) {
        response.add(new Message(command.execute(), user));
    }
}