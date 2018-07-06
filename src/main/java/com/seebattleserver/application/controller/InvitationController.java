package com.seebattleserver.application.controller;

import com.google.gson.Gson;
import com.seebattleserver.application.message.Message;
import com.seebattleserver.application.user.User;
import com.seebattleserver.application.user.UserRegistry;
import com.seebattleserver.application.user.UserStatus;
import com.seebattleserver.service.websocket.SocketHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.TextMessage;

import java.util.ArrayList;
import java.util.List;

public class RequestOpponentController implements Controller {
    private static final Logger LOGGER = LoggerFactory.getLogger(SocketHandler.class);

    private User user;
    private UserRegistry userRegistry;
    private Gson gson;
    private List<Message> response;

    public RequestOpponentController(User user, UserRegistry userRegistry, Gson gson) {
        this.user = user;
        this.userRegistry = userRegistry;
        this.gson = gson;
        response = new ArrayList<>();
    }

    @Override
    public List<Message> handle(TextMessage text) {
        String opponentName = getMessage(text);
        makeInvitation(opponentName);
        return response;
    }

    private String getMessage(TextMessage text) {
        Message message = gson.fromJson(text.getPayload(), Message.class);
        String opponentName = message.getContent().trim();
        return opponentName;
    }

    private void makeInvitation(String opponentName) {
        User opponent = userRegistry.getUserByName(opponentName);
        if (isInvitationPossible(opponent)) {
            invite(opponent);
        } else {
            notifyAboutMistake();
        }
    }

    private boolean isInvitationPossible(User userOpponent) {
        if ((isFree(user)) && (isFree(userOpponent)) && (isNotNull(userOpponent))) {
            return true;
        }
        return false;
    }

    private boolean isFree(User user) {
        if (user.getUserStatus().equals(UserStatus.FREE)) {
            return true;
        }
        return false;
    }

    private boolean isNotNull(User opponent) {
        if (opponent != null) {
            return true;
        }
        return false;
    }

    private void invite(User userOpponent) {
        userOpponent.setUserStatus(UserStatus.INVITED);
        user.setUserStatus(UserStatus.INVITING);
        unitOpponents(user, userOpponent);
        makeResponse(userOpponent);
    }

    private void makeResponse(User userOpponent) {
        response.add(new Message("С вами хочет играть " + user.getUsername() +
                ". Введите команду 'yes' или 'no'.", userOpponent));
        response.add(new Message("Запрос отправлен игроку " + userOpponent.getUsername() +
                " Дождитесь ответа.", user));
    }

    private void unitOpponents(User user, User opponent) {
        user.setOpponent(opponent);
        opponent.setOpponent(user);
    }

    private void notifyAboutMistake() {
        response.add(new Message("Соперник с данным именем не найден или не может принять приглашение", user));
        LOGGER.warn("Пользователь " + user.getUsername() + "ввел не валидное имя соперника");
    }
}
