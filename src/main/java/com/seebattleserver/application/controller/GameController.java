package com.seebattleserver.application.controller;

import com.google.gson.Gson;
import com.seebattleserver.application.gameregistry.GameRegistry;
import com.seebattleserver.application.message.Message;
import com.seebattleserver.application.user.User;
import com.seebattleserver.application.user.UserStatus;
import com.seebattleserver.domain.game.Game;
import com.seebattleserver.domain.game.Result;
import com.seebattleserver.domain.player.Player;
import com.seebattleserver.service.websocket.SocketHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.TextMessage;

import java.util.ArrayList;
import java.util.List;

public class GameController implements Controller {
    private static final Logger LOGGER = LoggerFactory.getLogger(SocketHandler.class);

    private User user;
    private GameRegistry gameRegistry;
    private Game game;
    private Gson gson;
    private List<Message> response;

    int x;
    char y;

    public GameController(User user, GameRegistry gameRegistry, Gson gson) {
        this.user = user;
        this.gameRegistry = gameRegistry;
        this.gson = gson;
        response = new ArrayList<>();
        game = gameRegistry.getGameByUser(user);
    }

    @Override
    public List<Message> handle(TextMessage text) {
        if (isUserMove()) {
            String coordinates = getMessage(text);
            makeMove(coordinates);
        } else {
            makeUserMoveMistakeResponse();
        }
        return response;
    }

    private boolean isUserMove() {
        if (user.getUserStatus() == UserStatus.IN_GAME_MOVE) {
            return true;
        }
        return false;
    }

    private String getMessage(TextMessage text) {
        Message message = gson.fromJson(text.getPayload(), Message.class);
        String coordinates = message.getContent().trim();
        return coordinates;
    }

    private void makeMove(String coordinates) {
        try {
            initCoordinates(coordinates);
            move();
        } catch (Exception ex) {
            LOGGER.warn("Введены неверные координаты пользователем " + user.getUsername());
            response.add(new Message("Введены неверные координаты, попробуйте еще раз", user));
        }
    }

    private void initCoordinates(String coordinates) throws Exception {
        x = coordinates.charAt(0);
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
        response.add(getResponseByResult(result));
    }

    private Message getResponseByResult(Result result) {
        switch (result) {
            case MISSED:
                return new Message("Мимо", user);
            case REPEATED:
                return new Message("Вы уже стреляли в эту клетку", user);
            case GOT:
                return new Message("Попадание", user);
            case KILLED:
                return new Message("Убит", user);
            default:
                LOGGER.error("Недопустимое действие с игровым объектом");
                throw new IllegalArgumentException("Недопустимое действие с игровым объектом");
        }
    }

    private void passMove() {
        User opponent = user.getOpponent();
        user.setUserStatus(UserStatus.IN_GAME);
        opponent.setUserStatus(UserStatus.IN_GAME_MOVE);
        response.add(new Message("Введите х и у", opponent));
    }

    private void endGame() {
        User winner = determineWinner();
        User looser = winner.getOpponent();
        changeStatuses(winner, looser);
        removeGame(winner, looser);
        response.add(new Message("Игра окончена. Вы выиграли!", winner));
        response.add(new Message("Игра окончена. Вы проиграли", looser));
    }

    private User determineWinner() {
        Player winner = game.determineWinner();
        if (user.getPlayer().equals(winner)) {
            return user;
        } else {
            return user.getOpponent();
        }
    }

    private void changeStatuses(User user, User userOpponent) {
        user.setUserStatus(UserStatus.FREE);
        userOpponent.setUserStatus(UserStatus.FREE);
        user.setOpponent(null);
        userOpponent.setOpponent(null);
    }

    private void removeGame(User user, User userOpponent) {
        gameRegistry.remove(user, game);
        gameRegistry.remove(userOpponent, game);
    }

    private void makeUserMoveMistakeResponse() {
        response.add(new Message("Не ваш ход", user));
    }
}
