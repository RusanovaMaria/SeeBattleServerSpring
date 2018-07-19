package com.seebattleserver.application.controller.commandcontroller;

import com.seebattleserver.application.command.*;
import com.seebattleserver.application.controller.Controller;
import com.seebattleserver.application.json.jsonmessage.jsonmessagehandler.DefaultJsonMessageHandler;
import com.seebattleserver.application.json.jsonmessage.jsonmessagehandler.JsonMessageHandler;
import com.seebattleserver.application.user.User;
import com.seebattleserver.application.json.jsonmessage.JsonMessage;
import com.seebattleserver.application.user.UserStatus;
import com.seebattleserver.service.sender.UserSender;
import org.springframework.web.socket.TextMessage;

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
    public void handle(TextMessage textMessage) {
        JsonMessageHandler defaultJsonMessageHandler = new DefaultJsonMessageHandler();
        String commandWord = defaultJsonMessageHandler.handle(textMessage);
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
        userSender.sendMessage(user, new JsonMessage(command.execute()));
    }
}