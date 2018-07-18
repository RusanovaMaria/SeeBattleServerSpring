package com.seebattleserver.application.controller.gamestartcontroller;

import com.seebattleserver.application.controller.Controller;
import com.seebattleserver.application.controller.gamestartcontroller.gamestart.ClassicGameStart;
import com.seebattleserver.application.controller.gamestartcontroller.gamestart.GameStart;
import com.seebattleserver.application.gameregistry.GameRegistry;
import com.seebattleserver.application.message.Message;
import com.seebattleserver.application.message.messagehandler.MessageHandler;
import com.seebattleserver.application.user.User;
import com.seebattleserver.application.user.UserStatus;
import com.seebattleserver.domain.gameobjectarrangement.DefaultGameObjectArrangement;
import com.seebattleserver.domain.player.Player;
import com.seebattleserver.domain.playingfield.ClassicPlayingField;
import com.seebattleserver.domain.playingfield.PlayingField;
import com.seebattleserver.service.sender.UserSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.TextMessage;

public class GameStartController implements Controller {
    private static final Logger LOGGER = LoggerFactory.getLogger(GameStartController.class);
    public static final String STANDARD_GAME_OBJECT_ARRANGEMENT_TYPE = "standard";
    public static final String USER_GAME_OBJECT_ARRANGEMENT_TYPE = "user";
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
    public void handle(TextMessage textMessage) {
        MessageHandler  messageHandler = new MessageHandler();
        String gameObjectArrangementType = messageHandler.handle(textMessage);
        if (isCorrectPositionType(gameObjectArrangementType)) {
           handleGameObjectArrangementType(gameObjectArrangementType);
        } else {
            notifyAboutNotCorrectArrangementType();
            LOGGER.warn("Пользователь" +user.getUsername()+ "ввел неверный тип расположения игровых объектов.");
        }
    }

    private boolean isCorrectPositionType(String positionType) {
        if ((positionType.equals(STANDARD_GAME_OBJECT_ARRANGEMENT_TYPE)) || (positionType.equals(USER_GAME_OBJECT_ARRANGEMENT_TYPE))) {
            return true;
        }
        return false;
    }

    private void handleGameObjectArrangementType(String gameObjectArrangementType) {
        switch (gameObjectArrangementType) {
            case STANDARD_GAME_OBJECT_ARRANGEMENT_TYPE:
                PlayingField playingField = new ClassicPlayingField();
                DefaultGameObjectArrangement arrangement = new DefaultGameObjectArrangement(playingField);
                arrangement.arrange();
                Player player = user.getPlayer();
                player.setPlayingField(playingField);
                startGameIfPossible();
                break;
            case USER_GAME_OBJECT_ARRANGEMENT_TYPE:
                user.setUserStatus(UserStatus.SET_UP_GAME_OJECTS);
                userSender.sendMessage(user, new Message("Начало расстановки игровых объектов"));
                break;
                default:
                    LOGGER.error("Неверный тип расположения игровых объектов");
                    throw new IllegalArgumentException("Неверный тип расположения игровых объектов");
        }
    }



    private void notifyAboutGameObjectArrangement() {
        userSender.sendMessage(user, new Message("Игровые объекты установлены\n"+
        "Игра начнется, когда ваш соперник установит игровые объекты."));
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

    private void notifyAboutNotCorrectArrangementType() {
        userSender.sendMessage(user, new Message("Введен неверный тип расположения игровых объектов." +
                "Попробуйте еще раз."));
    }
}
