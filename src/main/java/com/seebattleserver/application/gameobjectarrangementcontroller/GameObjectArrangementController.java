package com.seebattleserver.application.gameobjectarrangementcontroller;

import com.seebattleserver.application.controller.Controller;
import com.seebattleserver.application.controller.gamestartcontroller.ClassicGameStartController;
import com.seebattleserver.application.controller.gamestartcontroller.GameStartController;
import com.seebattleserver.application.gameregistry.GameRegistry;
import com.seebattleserver.application.message.Message;
import com.seebattleserver.application.message.messagehandler.MessageHandler;
import com.seebattleserver.application.user.User;
import com.seebattleserver.application.user.UserStatus;
import com.seebattleserver.domain.gameobjectarrangement.DefaultGameObjectArrangement;
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
        MessageHandler messageHandler = new MessageHandler();
        String gameObjectArrangementType = messageHandler.handle(textMessage);
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
        DefaultGameObjectArrangement defaultGameObjectArrangement = new DefaultGameObjectArrangement();
        PlayingField playingField = defaultGameObjectArrangement.arrange();
        Player player = user.getPlayer();
        player.setPlayingField(playingField);
    }

    private void startGameIfPossible() {
        GameStartController gameStartController = new ClassicGameStartController(user, gameRegistry, userSender);
        gameStartController.startGameIfPossible();
    }

    private boolean isUserGameObjectArrangementType(String gameObjectArrangementType) {
        if (gameObjectArrangementType.equals(USER_GAME_OBJECT_ARRANGEMENT_TYPE)) {
            return true;
        }
        return false;
    }

    private void startUserGameObjectArrangementForUser() {
        user.setUserStatus(UserStatus.SET_UP_GAME_OJECTS);
    }

    private void notifyAboutUserGameArrangementStart() {
        userSender.sendMessage(user, new Message("Начало расстановки игровых объектов"));
    }

    private void notifyAboutNotCorrectArrangementType() {
        userSender.sendMessage(user, new Message("Тип расположения игровых объектов введен неверно." +
                "Попробуйте еще раз"));
    }
}
