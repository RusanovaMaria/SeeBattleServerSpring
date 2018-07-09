package com.seebattleserver.application.controller.gamestartcontroller;

import com.seebattleserver.application.controller.Controller;
import com.seebattleserver.application.controller.gamestartcontroller.gameobjectpositioncontroller.GameObjectPositionController;
import com.seebattleserver.application.controller.gamestartcontroller.gameobjectpositioncontroller.StandardGameObjectPositionController;
import com.seebattleserver.application.controller.gamestartcontroller.gameobjectpositioncontroller.UserGameObjectPositionController;
import com.seebattleserver.application.controller.gamestartcontroller.gamestart.ClassicGameStart;
import com.seebattleserver.application.controller.gamestartcontroller.gamestart.GameStart;
import com.seebattleserver.application.gameregistry.GameRegistry;
import com.seebattleserver.application.message.Message;
import com.seebattleserver.application.user.User;
import com.seebattleserver.application.user.UserStatus;
import com.seebattleserver.service.sender.UserSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameStartController implements Controller {
    private static final Logger LOGGER = LoggerFactory.getLogger(GameStartController.class);

    public static final String STANDARD_POSITION_TYPE = "standard";
    public static final String USER_POSITION_TYPE = "user";

    private User user;
    private UserSender userSender;
    private GameRegistry gameRegistry;
    private User userOpponent;

    public GameStartController(User user, UserSender userSender,
                               GameRegistry gameRegistry) {
        this.user = user;
        this.userSender = userSender;
        this.gameRegistry = gameRegistry;
        userOpponent = user.getUserOpponent();
    }

    @Override
    public void handle(String positionType) {
        if (isCorrectPositionType(positionType)) {
           arrangeGameObjects(positionType);
           startGameIfPossible();
        } else {
            notifyAboutNotCorrectPosition();
            LOGGER.warn("Позьзователь" +user.getUsername()+ "ввел неверный тип расположения игровых объектов.");
        }
    }

    private boolean isCorrectPositionType(String positionType) {
        if ((positionType.equals(STANDARD_POSITION_TYPE)) || (positionType.equals(USER_POSITION_TYPE))) {
            return true;
        }
        return false;
    }

    private void arrangeGameObjects(String positionType) {
        GameObjectPositionController controller = determineGameObjectPositionController(positionType);
        controller.handle();
    }

    private GameObjectPositionController determineGameObjectPositionController(String positionType) {
        switch (positionType) {
            case STANDARD_POSITION_TYPE:
                return new StandardGameObjectPositionController();
            case USER_POSITION_TYPE:
                return new UserGameObjectPositionController();
                default:
                    LOGGER.error("Неверный тип расположения игровых объектов");
                    throw new IllegalArgumentException("Неверный тип расположения игровых объектов");
        }
    }

    private void startGameIfPossible() {
        if (userOpponentHasFirstMove()) {
            passSecondMoveToUser();
            startGame();
        } else {
            passFirstMoveToUser();
        }
    }

    private boolean userOpponentHasFirstMove() {
        if (userOpponent.getUserStatus() == UserStatus.IN_GAME_MOVE) {
            return true;
        }
        return false;
    }

    private void passSecondMoveToUser() {
        user.setUserStatus(UserStatus.IN_GAME);
    }

    private void startGame() {
        GameStart gameStart = new ClassicGameStart(user, userOpponent, gameRegistry, userSender);
        gameStart.start();
    }

    private void passFirstMoveToUser() {
        user.setUserStatus(UserStatus.IN_GAME_MOVE);
    }

    private void notifyAboutNotCorrectPosition() {
        userSender.sendMessage(user, new Message("Введен неверный тип расположения игровых объектов." +
                "Попробуйте еще раз."));
    }
}
