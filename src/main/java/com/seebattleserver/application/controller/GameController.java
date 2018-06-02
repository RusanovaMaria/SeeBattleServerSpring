package com.seebattleserver.application.controller;

import com.seebattleserver.application.gameset.GameRegistry;
import com.seebattleserver.application.message.Message;
import com.seebattleserver.application.user.User;
import com.seebattleserver.application.user.UserStatus;
import com.seebattleserver.domain.game.Game;
import com.seebattleserver.domain.game.Result;
import com.seebattleserver.service.sender.UserSender;

public class GameController implements Controller {
    private User user;
    private GameRegistry gameRegistry;
    private UserSender userSender;

    public GameController(User user, UserSender userSender, GameRegistry gameRegistry) {
        this.user = user;
        this.userSender = userSender;
        this.gameRegistry = gameRegistry;
    }

    @Override
    public void handle(String message) {
        if (user.getUserStatus() == UserStatus.IN_GAME_MOVE) {
            String coordinates = message.trim();
            makeMove(user, coordinates);
        } else {
            notifyAboutMistake();
        }
    }

    private void makeMove(User user, String coordinates) {
        int x = getX(coordinates);
        char y = getY(coordinates);

        Game game = gameRegistry.getGameByUser(user);
        Result result = game.fire(user.getPlayer(), x, y);
        getAnswerByResult(result);
        passMove();

    }

    private int getX(String coordinates) {
        int x = Integer.parseInt(coordinates.substring(0, 1));
        return x;
    }

    private char getY(String coordinates) {
        char y = coordinates.charAt(1);
        return y;
    }

    private void passMove() {
        User opponent = user.getOpponent();
        user.setUserStatus(UserStatus.IN_GAME);
        opponent.setUserStatus(UserStatus.IN_GAME_MOVE);
    }

    private void getAnswerByResult(Result result) {
        switch (result) {
            case MISSED:
                userSender.sendMessage(user, new Message("Мимо"));
                break;
            case REPEATED:
                userSender.sendMessage(user, new Message("Вы уже стреляли в эту клетку"));
                break;
            case GOT:
                userSender.sendMessage(user, new Message("Попадание"));
                break;
            case KILLED:
                userSender.sendMessage(user, new Message("Убит"));
        }
    }

    private void notifyAboutMistake() {
        userSender.sendMessage(user, new Message("Сейчас не ваш ход"));
    }
}
