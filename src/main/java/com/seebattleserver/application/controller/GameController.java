package com.seebattleserver.application.controller;

import com.seebattleserver.application.gameregistry.GameRegistry;
import com.seebattleserver.application.message.Message;
import com.seebattleserver.application.user.User;
import com.seebattleserver.application.user.UserStatus;
import com.seebattleserver.domain.game.Game;
import com.seebattleserver.domain.game.Result;
import com.seebattleserver.domain.player.Player;
import com.seebattleserver.service.sender.UserSender;

public class GameController implements Controller {
    private User user;
    private GameRegistry gameRegistry;
    private Game game;
    private UserSender userSender;

    public GameController(User user, UserSender userSender, GameRegistry gameRegistry) {
        this.user = user;
        this.userSender = userSender;
        this.gameRegistry = gameRegistry;
        game = gameRegistry.getGameByUser(user);
    }

    @Override
    public void handle(String message) {
        if (user.getUserStatus() == UserStatus.IN_GAME_MOVE) {
            String coordinates = message.trim();
            makeMove(user, coordinates);
            endMove();
        } else if (user.getUserStatus() == UserStatus.IN_GAME) {
            notifyAboutMistake();
        } else {
            throw new IllegalArgumentException("Не игровой статус пользователя");
        }
    }

    private void makeMove(User user, String coordinates) {
        int x = getX(coordinates);
        char y = getY(coordinates);

        Result result = game.fire(user.getPlayer(), x, y);
        sendAnswerByResult(result);
    }

    private int getX(String coordinates) {
        int x = Integer.parseInt(coordinates.substring(0, 1));
        return x;
    }

    private char getY(String coordinates) {
        char y = coordinates.charAt(1);
        return y;
    }

    private void endMove() {
        if (game.isEnd()) {
            endGame();
        } else {
            passMove();
        }
    }

    private void passMove() {
        User opponent = user.getOpponent();
        userSender.sendMessage(opponent, new Message("Введите координаты х и у"));
        user.setUserStatus(UserStatus.IN_GAME);
        opponent.setUserStatus(UserStatus.IN_GAME_MOVE);
    }

    private void sendAnswerByResult(Result result) {
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

    private void endGame() {
        User userWinner = determineUserWinner();
        notifyWinner(userWinner);
        notifyLooser(userWinner.getOpponent());
        User userOpponent = user.getOpponent();
        removeOpponents(user, userOpponent);
        changeStatuses(user, userOpponent);
        removeGame(user, userOpponent);
    }

    private User determineUserWinner() {
        Player winner = game.determineWinner();
        if (user.getPlayer().equals(winner)) {
            return user;
        } else {
            return user.getOpponent();
        }
    }

    private void notifyWinner(User winnerUser) {
        userSender.sendMessage(winnerUser, new Message("Вы победили, игра окончена"));
    }

    private void notifyLooser(User looserUser) {
        userSender.sendMessage(looserUser, new Message("Вы проиграли, игра окончена"));
    }

    private void notifyAboutMistake() {
        userSender.sendMessage(user, new Message("Сейчас не ваш ход"));
    }

    private void removeOpponents(User user, User userOpponent) {
        user.setOpponent(null);
        userOpponent.setOpponent(null);
    }

    private void changeStatuses(User user, User userOpponent) {
        user.setUserStatus(UserStatus.FREE);
        userOpponent.setUserStatus(UserStatus.FREE);
    }

    private void removeGame(User user, User userOpponent) {
        gameRegistry.remove(user, game);
        gameRegistry.remove(userOpponent, game);
    }
}
