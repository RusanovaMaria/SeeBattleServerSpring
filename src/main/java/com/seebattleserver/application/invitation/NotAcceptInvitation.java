package com.seebattleserver.application.invitation;

import com.seebattleserver.application.message.Message;
import com.seebattleserver.application.user.User;
import com.seebattleserver.application.user.UserStatus;
import com.seebattleserver.service.sender.UserSender;

public class NotAcceptInvitation implements Invitation {

    private User user;
    private User userOpponent;
    private UserSender userSender;

    public NotAcceptInvitation(User user, UserSender userSender) {
        this.user = user;
        this.userSender = userSender;
        userOpponent = user.getUserOpponent();
    }

    @Override
    public void handleAnswer() {
        sendAnswer();
        changeStatuses();
    }

    private void sendAnswer() {
        userSender.sendMessage(userOpponent, new Message("Игрок " + user.getUsername() + " отклонил ваше предложение"));
    }

    private void changeStatuses() {
        user.setUserOpponent(null);
        user.setUserStatus(UserStatus.FREE);
        userOpponent.setUserOpponent(null);
        userOpponent.setUserStatus(UserStatus.FREE);
    }
}
