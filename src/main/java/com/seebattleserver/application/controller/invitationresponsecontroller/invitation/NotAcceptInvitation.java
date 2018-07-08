package com.seebattleserver.application.controller.invitationresponsecontroller.invitation;

import com.seebattleserver.application.controller.invitationresponsecontroller.invitation.Invitation;
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
        severOpponents();
    }

    private void sendAnswer() {
        userSender.sendMessage(userOpponent, new Message("Игрок " + user.getUsername() + " отклонил ваше предложение"));
    }

    private void changeStatuses() {
        user.setUserStatus(UserStatus.FREE);
        userOpponent.setUserStatus(UserStatus.FREE);
    }

    private void severOpponents() {
        user.setUserOpponent(null);
        userOpponent.setUserOpponent(null);
    }
}
