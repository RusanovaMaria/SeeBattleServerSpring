package com.seebattleserver.application.controller.gameobjectarrangementcontroller.gamestarthandler;

import com.seebattleserver.application.gameregistry.GameRegistry;
import com.seebattleserver.application.json.jsonmessage.JsonMessage;
import com.seebattleserver.application.user.User;
import com.seebattleserver.application.user.UserStatus;
import com.seebattleserver.domain.game.ClassicGame;
import com.seebattleserver.domain.game.Game;
import com.seebattleserver.service.sender.UserSender;

public class ClassicGameStartHandler implements GameStartHandler {
    private User user;
    private User userOpponent;
    private GameRegistry gameRegistry;
    private UserSender userSender;

    public ClassicGameStartHandler(User user, GameRegistry gameRegistry, UserSender userSender) {
        this.user = user;
        userOpponent = user.getUserOpponent();
        this.gameRegistry = gameRegistry;
        this.userSender = userSender;
    }

    public void startGameIfPossible() {
        if (userOpponentHasFirstMove()) {
            passSecondMoveToUser();
            startGame();
        } else {
            passFirstMoveToUser();
            notifyAboutGameObjectArrangementEnd();
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

    private void passFirstMoveToUser() {
        user.setUserStatus(UserStatus.IN_GAME_MOVE);
    }

    public void startGame() {
        Game game = new ClassicGame(user.getPlayer(), userOpponent.getPlayer());
        gameRegistry.put(user, game);
        gameRegistry.put(userOpponent, game);
        notifyAboutGameStart();
    }

    private void notifyAboutGameStart() {
        if (user.getUserStatus() == UserStatus.IN_GAME_MOVE) {
            userSender.sendMessage(user, new JsonMessage("Игра началась. Введите х и у"));
            userSender.sendMessage(userOpponent, new JsonMessage("Игра началась. Дождитесь хода соперника"));
        } else {
            userSender.sendMessage(userOpponent, new JsonMessage("Игра началась. Введите х и у"));
            userSender.sendMessage(user, new JsonMessage("Игра началась. Дождитесь хода соперника"));
        }
    }

    private void notifyAboutGameObjectArrangementEnd() {
        userSender.sendMessage(user, new JsonMessage("Игровые объекты успешно установлены"));
        userSender.sendMessage(user, new JsonMessage(
                "Игра начнется, когда ваш соперник установит игровые объекты."));
    }
}

