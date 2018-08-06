package com.seebattleserver.application.controller.invitationcontroller;

import com.seebattleserver.application.controller.Controller;
import com.seebattleserver.application.json.jsonmessage.JsonMessage;
import com.seebattleserver.application.json.jsonmessage.jsonmessagehandler.DefaultJsonMessageHandler;
import com.seebattleserver.application.json.jsonmessage.jsonmessagehandler.JsonMessageHandler;
import com.seebattleserver.application.user.User;
import com.seebattleserver.application.user.UserRegistry;
import com.seebattleserver.application.user.UserStatus;
import com.seebattleserver.service.sender.UserSender;
import org.springframework.web.socket.TextMessage;

public class InvitationController implements Controller {
    private User user;
    private UserRegistry userRegistry;
    private UserSender userSender;

    public InvitationController(User user, UserRegistry userRegistry,
                                UserSender userSender) {
        this.user = user;
        this.userRegistry = userRegistry;
        this.userSender = userSender;
    }

    @Override
    public void handle(TextMessage textMessage) {
        JsonMessageHandler defaultJsonMessageHandler = new DefaultJsonMessageHandler();
        String userOpponentName = defaultJsonMessageHandler.handle(textMessage);
        User userOpponent = userRegistry.getUserByName(userOpponentName);
        if (isInvitationPossible(userOpponent)) {
            invite(userOpponent);
            notifyAboutSendInvitation();
        } else {
            notifyAboutMistake();
        }
    }

    private boolean isInvitationPossible(User userOpponent) {
        if ((isNotNull(userOpponent) && (isFree(userOpponent)))) {
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
        uniteOpponents(user, userOpponent);
        sendInvitation(userOpponent);
    }

    private void uniteOpponents(User user, User opponent) {
        user.setUserOpponent(opponent);
        opponent.setUserOpponent(user);
    }

    private void sendInvitation(User userOpponent) {
        userSender.sendMessage(userOpponent, new JsonMessage("С вами хочет " +
                "играть " + user.getUsername() + ". Введите команду 'yes' или 'no'."));
    }

    private void notifyAboutSendInvitation() {
        userSender.sendMessage(user, (new JsonMessage("Запрос отправлен. Дождитесь ответа.")));
    }

    private void notifyAboutMistake() {
        userSender.sendMessage(user, new JsonMessage("Данный пользователь " +
                "не обнаружен или не может принять приглашение"));
    }
}
