package com.seebattleserver.application.controller.gamecontroller;

import com.google.gson.Gson;
import com.seebattleserver.application.controller.Controller;
import com.seebattleserver.application.gameregistry.GameRegistry;
import com.seebattleserver.application.message.Message;
import com.seebattleserver.application.user.User;
import com.seebattleserver.application.user.UserStatus;
import com.seebattleserver.domain.game.Game;
import com.seebattleserver.domain.game.Result;
import com.seebattleserver.domain.player.Player;
import com.seebattleserver.service.sender.UserSender;
import com.seebattleserver.service.websocket.SocketHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameController implements Controller {
    private static final Logger LOGGER = LoggerFactory.getLogger(SocketHandler.class);

    private User user;
    private GameRegistry gameRegistry;
    private Game game;
    private Gson gson;
    private UserSender userSender;
    private User userOpponent;

    int x;
    char y;

    public GameController(User user, GameRegistry gameRegistry, Gson gson,
                          UserSender userSender) {
        this.user = user;
        this.gameRegistry = gameRegistry;
        this.gson = gson;
        this.userSender = userSender;
        game = gameRegistry.getGameByUser(user);
        userOpponent = user.getUserOpponent();
    }

    @Override
    public void handle(String coordinates) {
        if (isUserMove()) {
            makeMove(coordinates);
        } else {
            notifyAboutNotUserMove();
        }
    }

    private boolean isUserMove() {
        if (user.getUserStatus() == UserStatus.IN_GAME_MOVE) {
            return true;
        }
        return false;
    }

    private void makeMove(String coordinates) {
        try {
            initCoordinates(coordinates);
            move();
        } catch (Exception ex) {
            notifyAboutNotCorrectCoordinatesInput();
            LOGGER.warn("Введены неверные координаты пользователем " + user.getUsername());
        }
    }

    private void notifyAboutNotCorrectCoordinatesInput() {
        userSender.sendMessage(user, new Message("Введены неверные координаты, попробуйте еще раз"));
    }

    private void initCoordinates(String coordinates)  {
        char c = coordinates.charAt(0);
        x = Character.getNumericValue(c);
        y = coordinates.charAt(1);
    }

    private void move() {
        makeShot(x, y);
        if (!game.isEnd()) {
            passMove();
        } else {
            endGame();
        }
    }

    private void makeShot(int x, char y) {
        Result result = game.fire(user.getPlayer(), x, y);
        sendResult(result);
    }

    private void sendResult(Result result) {
        userSender.sendMessage(user, getResponseByResult(result));
    }

    private Message getResponseByResult(Result result) {
        switch (result) {
            case MISSED:
                return new Message("Мимо");
            case REPEATED:
                return new Message("Вы уже стреляли в эту клетку");
            case GOT:
                return new Message("Попадание");
            case KILLED:
                return new Message("Убит");
            default:
                LOGGER.error("Недопустимое действие с игровым объектом");
                throw new IllegalArgumentException("Недопустимое действие с игровым объектом");
        }
    }

    private void passMove() {
        User userOpponent = user.getUserOpponent();
        user.setUserStatus(UserStatus.IN_GAME);
        userOpponent.setUserStatus(UserStatus.IN_GAME_MOVE);
        notifyAboutPassMove(userOpponent);
    }

    private void notifyAboutPassMove(User userOpponent) {
        userSender.sendMessage(userOpponent, new Message("Введите х и у"));
    }

    private void endGame() {
        User winner = determineWinner();
        User looser = winner.getUserOpponent();
        notifyAboutGameEnd(winner, looser);
        endGameProcessForUsers();
        removeGame();
    }

    private void notifyAboutGameEnd(User winner, User looser) {
        userSender.sendMessage(winner, new Message("Игра окончена. Вы выиграли!"));
        userSender.sendMessage(looser, new Message("Игра окончена. Вы проиграли"));
    }

    private User determineWinner() {
        Player winner = game.determineWinner();
        if (user.getPlayer().equals(winner)) {
            return user;
        } else {
            return user.getUserOpponent();
        }
    }

    private void endGameProcessForUsers() {
        user.setUserStatus(UserStatus.FREE);
        userOpponent.setUserStatus(UserStatus.FREE);
        user.setUserOpponent(null);
        userOpponent.setUserOpponent(null);
    }

    private void removeGame() {
        gameRegistry.remove(user, game);
        gameRegistry.remove(userOpponent, game);
    }

    private void notifyAboutNotUserMove() {
        userSender.sendMessage(user, new Message("Сейчас не ваш ход"));
    }
}
