package com.seebattleserver.application.controller.nameentercontroller;

import com.seebattleserver.application.controller.Controller;
import com.seebattleserver.application.message.Message;
import com.seebattleserver.application.message.messagehandler.MessageHandler;
import com.seebattleserver.application.user.User;
import com.seebattleserver.application.user.UserRegistry;
import com.seebattleserver.application.user.UserStatus;
import com.seebattleserver.service.sender.UserSender;
import org.springframework.web.socket.TextMessage;

public class NameEnterController implements Controller {
    private User user;
    private UserRegistry userRegistry;
    private UserSender userSender;

    public NameEnterController(User user, UserRegistry userRegistry, UserSender userSender) {
        this.user = user;
        this.userRegistry = userRegistry;
        this.userSender = userSender;
    }

    @Override
    public void handle(TextMessage textMessage) {
        MessageHandler messageHandler = new MessageHandler();
        String name = messageHandler.handle(textMessage);
        User testUser = userRegistry.getUserByName(name);
        if(userDoesNotExist(testUser)) {
           setNameForUserAndChangeStatus(name);
        } else {
            notifyAboutNameRepeat();
        }
    }

    private boolean userDoesNotExist(User user) {
        if (user == null) {
            return true;
        }
        return false;
    }

    private void setNameForUserAndChangeStatus(String name) {
        user.setUsername(name);
        user.setUserStatus(UserStatus.FREE);
    }

    private void notifyAboutNameRepeat() {
        userSender.sendMessage(user, new Message("Данное имя уже существует. Введите другое имя"));
    }
}
