package com.seebattleserver.application.controller.gameobjectpositiontypecontroller;

import com.seebattleserver.application.controller.Controller;
import com.seebattleserver.application.controller.gameobjectpositiontypecontroller.gameobjectpositiontype.GameObjectPositionType;
import com.seebattleserver.application.controller.gameobjectpositiontypecontroller.gameobjectpositiontype.StandardGameObjectPositionType;
import com.seebattleserver.application.controller.gameobjectpositiontypecontroller.gameobjectpositiontype.UserGameObjectPositionType;
import com.seebattleserver.application.message.Message;
import com.seebattleserver.application.user.User;
import com.seebattleserver.domain.player.Player;
import com.seebattleserver.service.sender.UserSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameObjectPositionTypeController implements Controller {
    private static final Logger LOGGER = LoggerFactory.getLogger(GameObjectPositionTypeController.class);

    private static final String STANDARD_GAME_OBJECT_POSITION_TYPE = "standard";
    private static final String USER_GAME_OBJECT_POSITION_TYPE = "user";

    private User user;
    private UserSender userSender;

    public GameObjectPositionTypeController(User user, UserSender userSender) {
        this.user = user;
        this.userSender = userSender;
    }

    @Override
    public void handle(String gameObjectPositionType) {
        if (isCorrectGameObjectPositionType(gameObjectPositionType)) {
            GameObjectPositionType positionType = determineGameObjectPositionType(gameObjectPositionType);
            positionType.apply();
        } else {
            notifyAboutNotCorrectType();
        }
    }

    private boolean isCorrectGameObjectPositionType(String positionType) {
        if ((positionType.equals(STANDARD_GAME_OBJECT_POSITION_TYPE)) ||
                (positionType.equals(USER_GAME_OBJECT_POSITION_TYPE))) {
            return true;
        }
        return false;
    }

    private GameObjectPositionType determineGameObjectPositionType(String positionType) {
        switch (positionType) {
            case STANDARD_GAME_OBJECT_POSITION_TYPE:
                return new StandardGameObjectPositionType();
            case USER_GAME_OBJECT_POSITION_TYPE:
                return new UserGameObjectPositionType();
                default:
                    LOGGER.error("Неверный тип расположения игровых объектов");
                    throw new IllegalArgumentException("Неверный тип расположения игровых объектов");
        }
    }

    private void notifyAboutNotCorrectType() {
        userSender.sendMessage(user, new Message("Введен неверный тип расположения игровых объектов"));
    }
}
