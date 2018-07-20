package com.seebattleserver.application.controller.gameobjectarrangementcontroller;

import com.seebattleserver.application.controller.Controller;
import com.seebattleserver.application.controller.gameobjectarrangementcontroller.defaultgameobjectarrangementhandler.DefaultGameObjectArrangementHandler;
import com.seebattleserver.application.controller.gameobjectarrangementcontroller.gamestarthandler.ClassicGameStartHandler;
import com.seebattleserver.application.controller.gameobjectarrangementcontroller.gamestarthandler.GameStartHandler;
import com.seebattleserver.application.gameregistry.GameRegistry;
import com.seebattleserver.application.json.jsonmessage.JsonMessage;
import com.seebattleserver.application.json.jsonmessage.jsonmessagehandler.DefaultJsonMessageHandler;
import com.seebattleserver.application.user.User;
import com.seebattleserver.application.user.UserStatus;
import com.seebattleserver.domain.player.Player;
import com.seebattleserver.domain.playingfield.PlayingField;
import com.seebattleserver.service.sender.UserSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.TextMessage;

public class GameObjectArrangementController implements Controller {
    private static final Logger LOGGER = LoggerFactory.getLogger(GameObjectArrangementController.class);
    public static final String DEFAULT_GAME_OBJECT_ARRANGEMENT_TYPE = "default";
    public static final String USER_GAME_OBJECT_ARRANGEMENT_TYPE = "user";
    private User user;
    private UserSender userSender;
    private GameRegistry gameRegistry;
    private User userOpponent;

    public GameObjectArrangementController(User user, UserSender userSender,
                                           GameRegistry gameRegistry) {
        this.user = user;
        this.userSender = userSender;
        this.gameRegistry = gameRegistry;
        userOpponent = user.getUserOpponent();
    }

    @Override
    public void handle(TextMessage textMessage) {
        DefaultJsonMessageHandler defaultJsonMessageHandler = new DefaultJsonMessageHandler();
        String gameObjectArrangementType = defaultJsonMessageHandler.handle(textMessage);
        if (isCorrectPositionType(gameObjectArrangementType)) {
            handleGameObjectArrangementType(gameObjectArrangementType);
        } else {
            notifyAboutNotCorrectArrangementType();
            LOGGER.warn("Пользователь" + user.getUsername() + "ввел неверный тип расположения игровых объектов.");
        }
    }

    private boolean isCorrectPositionType(String positionType) {
        if ((positionType.equals(DEFAULT_GAME_OBJECT_ARRANGEMENT_TYPE)) || (positionType.equals(USER_GAME_OBJECT_ARRANGEMENT_TYPE))) {
            return true;
        }
        return false;
    }

    private void handleGameObjectArrangementType(String gameObjectArrangementType) {
        if (isDefaultGameObjectArrangementType(gameObjectArrangementType)) {
            arrangeGameObjectsByDefault();
            startGameIfPossible();
        } else if (isUserGameObjectArrangementType(gameObjectArrangementType)) {
            startUserGameObjectArrangementForUser();
            notifyAboutUserGameArrangementStart();
        }
    }

    private boolean isDefaultGameObjectArrangementType(String gameObjectArrangementType) {
        if (gameObjectArrangementType.equals(DEFAULT_GAME_OBJECT_ARRANGEMENT_TYPE)) {
            return true;
        }
        return false;
    }

    private void arrangeGameObjectsByDefault() {
        PlayingField playingField = user.getPlayer().getPlayingField();
        DefaultGameObjectArrangementHandler defaultGameObjectArrangementHandler = new DefaultGameObjectArrangementHandler();
        defaultGameObjectArrangementHandler.arrangeGameObjectsByDefault(playingField);
    }

    private void startGameIfPossible() {
        GameStartHandler gameStartHandler = new ClassicGameStartHandler(user, gameRegistry, userSender);
        gameStartHandler.startGameIfPossible();
    }

    private boolean isUserGameObjectArrangementType(String gameObjectArrangementType) {
        if (gameObjectArrangementType.equals(USER_GAME_OBJECT_ARRANGEMENT_TYPE)) {
            return true;
        }
        return false;
    }

    private void startUserGameObjectArrangementForUser() {
        user.setUserStatus(UserStatus.SET_UP_GAME_OBJECTS);
    }

    private void notifyAboutUserGameArrangementStart() {
        userSender.sendMessage(user, new JsonMessage("Начало расстановки игровых объектов"));
    }

    private void notifyAboutNotCorrectArrangementType() {
        userSender.sendMessage(user, new JsonMessage("Тип расположения игровых объектов введен неверно." +
                "Попробуйте еще раз"));
    }
}
