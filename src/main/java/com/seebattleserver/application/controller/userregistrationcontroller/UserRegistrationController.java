package com.seebattleserver.application.controller.userregistrationcontroller;

import com.seebattleserver.application.controller.Controller;
import com.seebattleserver.application.json.jsonmessage.JsonMessage;
import com.seebattleserver.application.json.jsonmessage.jsonmessagehandler.DefaultJsonMessageHandler;
import com.seebattleserver.application.json.jsonmessage.jsonmessagehandler.JsonMessageHandler;
import com.seebattleserver.application.user.User;
import com.seebattleserver.application.user.UserRegistry;
import com.seebattleserver.application.user.UserStatus;
import com.seebattleserver.service.sender.UserSender;
import org.springframework.web.socket.TextMessage;

public class UserRegistrationController implements Controller {
    private User user;
    private UserRegistry userRegistry;
    private UserSender userSender;

    public UserRegistrationController(User user, UserRegistry userRegistry, UserSender userSender) {
        this.user = user;
        this.userRegistry = userRegistry;
        this.userSender = userSender;
    }

    @Override
    public void handle(TextMessage textMessage) {
        JsonMessageHandler defaultJsonMessageHandler = new DefaultJsonMessageHandler();
        String name = defaultJsonMessageHandler.handle(textMessage);
        registerUserIfPossible(name);
    }

    private void registerUserIfPossible(String name) {
        User testUser = userRegistry.getUserByName(name);
        if (userDoesNotExist(testUser)) {
            registerUser(name);
            notifyAboutSuccessfulRegistration();
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

    private void registerUser(String name) {
        user.setUsername(name);
        userRegistry.add(user);
        user.setUserStatus(UserStatus.FREE);
    }

    private void notifyAboutSuccessfulRegistration() {
        userSender.sendMessage(user, new JsonMessage("Регистрация успешно завршена." +
                "Для просмотра списка возможных действий введите команду help."));
    }

    private void notifyAboutNameRepeat() {
        userSender.sendMessage(user, new JsonMessage("Данное имя уже существует. Введите другое имя"));
    }
}
