package com.seebattleserver.application.controller.gamestartcontroller;

import com.seebattleserver.application.controller.Controller;
import com.seebattleserver.application.gameregistry.GameRegistry;
import com.seebattleserver.application.user.User;
import com.seebattleserver.service.sender.UserSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameStartController implements Controller {
    private static final Logger LOGGER = LoggerFactory.getLogger(GameStartController.class);

    public static final String STANDARD_POSITION = "standard";
    public static final String USER_POSITION = "user";

    private User user;
    private UserSender userSender;
    private GameRegistry gameRegistry;

    public GameStartController(User user, UserSender userSender,
                               GameRegistry gameRegistry) {
        this.user = user;
        this.userSender = userSender;
        this.gameRegistry = gameRegistry;
    }

    @Override
    public void handle(String position) {

    }
}
