package com.seebattleserver.application.controller.invitationresponsecontroller.invitation;

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
        sensAnswerToOpponent();
        changeStatuses();
        severOpponents();
        notifyAboutSuccessfulNay();
    }

    private void notifyAboutSuccessfulNay() {
        userSender.sendMessage(user, new Message("Вы отклонили приглашение игрока " +userOpponent.getUsername()+ "."));
    }

    private void sensAnswerToOpponent() {
        userSender.sendMessage(userOpponent, new Message("Игрок " + user.getUsername() + " отклонил ваше приглашение"));
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
