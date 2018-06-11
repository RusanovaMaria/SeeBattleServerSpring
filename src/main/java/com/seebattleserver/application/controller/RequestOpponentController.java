package com.seebattleserver.application.controller;

import com.seebattleserver.application.message.Message;
import com.seebattleserver.application.user.User;
import com.seebattleserver.application.user.UserRegistry;
import com.seebattleserver.application.user.UserStatus;
import com.seebattleserver.service.sender.UserSender;

public class RequestOpponentController implements Controller {

    private User user;
    private UserRegistry userRegistry;
    private UserSender userSender;

    public RequestOpponentController(User user, UserRegistry userRegistry, UserSender userSender) {
        this.user = user;
        this.userRegistry = userRegistry;
        this.userSender = userSender;
    }

    @Override
    public void handle(String message) {
        makeInvitation(message);
    }

    private void makeInvitation(String opponentName) {
        User opponent = userRegistry.getUserByName(opponentName);
        if (isOpponentFree(opponent)) {
            invite(user, opponent);
        } else {
            notifyAboutMistake();
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
