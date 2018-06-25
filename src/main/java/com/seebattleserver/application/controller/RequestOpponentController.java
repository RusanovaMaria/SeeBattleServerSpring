package com.seebattleserver.application.controller;

import com.google.gson.Gson;
import com.seebattleserver.application.message.Message;
import com.seebattleserver.application.user.User;
import com.seebattleserver.application.user.UserRegistry;
import com.seebattleserver.application.user.UserStatus;
import com.seebattleserver.service.sender.UserSender;
import com.seebattleserver.service.websocket.SocketHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.TextMessage;

public class RequestOpponentController implements Controller {

    private static final Logger LOGGER = LoggerFactory.getLogger(SocketHandler.class);

    private User user;
    private UserRegistry userRegistry;
    private UserSender userSender;
    private Gson gson;

    public RequestOpponentController(User user, UserRegistry userRegistry, UserSender userSender, Gson gson) {
        this.user = user;
        this.userRegistry = userRegistry;
        this.userSender = userSender;
        this.gson = gson;
    }

    @Override
    public void handle(TextMessage text) {
        Message message = gson.fromJson(text.getPayload(), Message.class);
        String opponentName = message.getContent();
        makeInvitation(opponentName);
    }

    private void makeInvitation(String opponentName) {
        User opponent = userRegistry.getUserByName(opponentName);
        if (isOpponentFree(opponent)) {
            invite(user, opponent);
        } else {
            notifyAboutMistake();
            LOGGER.info("Пользователь с имнем " +opponentName+ "не обнаружен");
        }
    }

    private boolean isOpponentFree(User userOpponent) {
        if (userOpponent.getUserStatus().equals(UserStatus.FREE)) {
            return true;
        }
        return false;
    }

    private void invite(User user, User userOpponent) {
        userOpponent.setUserStatus(UserStatus.INVITED);
        user.setUserStatus(UserStatus.INVITING);

        sendInvitationToOpponent(userOpponent, user);
        unitOpponents(user, userOpponent);
    }

    private void sendInvitationToOpponent(User userOpponent, User user) {
        userSender.sendMessage(userOpponent, new Message("С вами хочет играть " + user.getUsername() +
                ". Введите команду 'yes' или 'no'"));
    }

    private void unitOpponents(User user, User opponent) {
        user.setOpponent(opponent);
        opponent.setOpponent(user);
    }

    private void notifyAboutMistake() {
        userSender.sendMessage(user, new Message("Соперник с таким именем не найден или не может принять приглашение"));
    }
}
