package com.seebattleserver.application.invitation;

import com.seebattleserver.application.message.Message;
import com.seebattleserver.application.user.User;
import com.seebattleserver.application.user.UserStatus;
import com.seebattleserver.service.sender.UserSender;

public class NotAcceptInvitation implements Invitation {

    private User user;
    private UserSender userSender;

    public NotAcceptInvitation(User user, UserSender userSender) {
        this.user = user;
        this.userSender = userSender;
    }

    @Override
    public void handleAnswer() {
        notAcceptInvitation();
    }

    private void notAcceptInvitation() {
        User userOpponent = user.getOpponent();
        notifyOpponent(userOpponent);
        user.setOpponent(null);
        user.setUserStatus(UserStatus.FREE);
    }

    private void notifyOpponent(User userOpponent) {
        userSender.sendMessage(userOpponent, new Message("Игрок"+ user.getUsername()+"отклонил ваше предложение"));
    }
}
