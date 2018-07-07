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

public class InvitationController implements Controller {
    private static final Logger LOGGER = LoggerFactory.getLogger(SocketHandler.class);

    private User user;
    private UserRegistry userRegistry;
    private Gson gson;
    private UserSender userSender;

    public InvitationController(User user, UserRegistry userRegistry, Gson gson,
                                UserSender userSender) {
        this.user = user;
        this.userRegistry = userRegistry;
        this.gson = gson;
        this.userSender = userSender;
    }

    @Override
    public void handle(TextMessage text) {
        String userOpponentName = getMessage(text);
        User userOpponent = userRegistry.getUserByName(userOpponentName);
        if (isInvitationPossible(userOpponent)) {
            invite(userOpponent);
            notifyAboutSendInvitation();
        } else {
            notifyAboutMistake();
        }
    }

    private String getMessage(TextMessage text) {
        Message message = gson.fromJson(text.getPayload(), Message.class);
        String opponentName = message.getContent().trim();
        return opponentName;
    }

    private boolean isInvitationPossible(User userOpponent) {
        if (isFree(userOpponent) && (isNotNull(userOpponent))) {
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
        user.setUserStatus(UserStatus.INVITING);
        userOpponent.setUserStatus(UserStatus.INVITED);
        rally(user, userOpponent);
        sendInvitation(userOpponent);
    }

    private void rally(User user, User opponent) {
        user.setUserOpponent(opponent);
        opponent.setUserOpponent(user);
    }

    private void sendInvitation(User userOpponent) {
        userSender.sendMessage(userOpponent, new Message("С вами хочет " +
                "играть " + user.getUsername() + ". Введите команду 'yes' или 'no'."));
    }

    private void notifyAboutSendInvitation() {
        userSender.sendMessage(user, (new Message("Запрос отправлен. Дождитесь ответа.")));
    }

    private void notifyAboutMistake() {
        userSender.sendMessage(user, new Message("Данный пользователь " +
                "не обнаружен или не может принять приглашение"));
    }
}
