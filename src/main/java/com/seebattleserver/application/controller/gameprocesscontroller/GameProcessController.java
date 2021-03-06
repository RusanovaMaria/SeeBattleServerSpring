package com.seebattleserver.application.controller.gameprocesscontroller;

import com.seebattleserver.application.controller.Controller;
import com.seebattleserver.application.controller.gameprocesscontroller.gameendhandler.GameEndHandler;
import com.seebattleserver.application.gameregistry.GameRegistry;
import com.seebattleserver.application.json.jsonmessage.JsonMessage;
import com.seebattleserver.application.json.jsonmessage.jsonmessagehandler.DefaultJsonMessageHandler;
import com.seebattleserver.application.json.jsonmessage.jsonmessagehandler.JsonMessageHandler;
import com.seebattleserver.application.user.User;
import com.seebattleserver.application.user.UserStatus;
import com.seebattleserver.domain.game.Game;
import com.seebattleserver.domain.game.Result;
import com.seebattleserver.service.sender.UserSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.TextMessage;

public class GameProcessController implements Controller {
    private static final Logger LOGGER = LoggerFactory.getLogger(GameProcessController.class);
    private User user;
    private GameRegistry gameRegistry;
    private Game game;
    private UserSender userSender;
    private User userOpponent;
    int x;
    char y;

    public GameProcessController(User user, GameRegistry gameRegistry,
                                 UserSender userSender) {
        this.user = user;
        this.gameRegistry = gameRegistry;
        this.userSender = userSender;
        game = gameRegistry.getGameByUser(user);
        userOpponent = user.getUserOpponent();
    }

    @Override
    public void handle(TextMessage textMessage) {
        JsonMessageHandler defaultJsonMessageHandler = new DefaultJsonMessageHandler();
        String coordinates = defaultJsonMessageHandler.handle(textMessage);
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
        userSender.sendMessage(user, new JsonMessage("Введены неверные координаты, попробуйте еще раз"));
    }

    private void initCoordinates(String coordinates) throws Exception  {
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

    private JsonMessage getResponseByResult(Result result) {
        switch (result) {
            case MISSED:
                return new JsonMessage("Мимо");
            case REPEATED:
                return new JsonMessage("Вы уже стреляли в эту клетку");
            case GOT:
                return new JsonMessage("Попадание");
            case KILLED:
                return new JsonMessage("Убит");
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
        userSender.sendMessage(userOpponent, new JsonMessage("Введите х и у"));
    }

    private void endGame() {
        GameEndHandler gameEndHandler = new GameEndHandler(user, userOpponent, game, gameRegistry, userSender);
        gameEndHandler.endGame();
    }

    private void notifyAboutNotUserMove() {
        userSender.sendMessage(user, new JsonMessage("Сейчас не ваш ход"));
    }
}
