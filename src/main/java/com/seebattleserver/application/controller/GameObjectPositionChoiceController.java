package com.seebattleserver.application.controller;

import com.google.gson.Gson;
import com.seebattleserver.application.user.User;
import com.seebattleserver.service.sender.UserSender;
import org.springframework.web.socket.TextMessage;

public class GameObjectPositionChoiceController implements Controller {
    private static final String STANDARD_POSITION_COMMAND = "standard";
    private static final String USER_POSITION_COMMAND = "user";

    private User user;
    private Gson gson;
    private UserSender userSender;

    public GameObjectPositionChoiceController(User user, Gson gson,
                                              UserSender userSender) {
        this.user = user;
        this.gson = gson;
        this.userSender = userSender;
    }

    @Override
    public void handle(String coordinates) {

    }

    private boolean isCorrectCommand(String command) {
        if ((command.equals(STANDARD_POSITION_COMMAND)) || (command.equals(USER_POSITION_COMMAND))) {
            return true;
        }
        return false;
    }
}
