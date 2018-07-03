package com.seebattleserver.application.invitation;

import com.seebattleserver.application.message.Message;
import com.seebattleserver.application.user.User;
import com.seebattleserver.application.user.UserStatus;

import java.util.ArrayList;
import java.util.List;

public class NotAcceptInvitation implements Invitation {

    private User user;
    private List<Message> response;
    private User userOpponent;

    public NotAcceptInvitation(User user) {
        this.user = user;
        userOpponent = user.getOpponent();
        response = new ArrayList<>();
    }

    @Override
    public List<Message> handleAnswer() {
        notAcceptInvitation();
        return response;
    }

    private void notAcceptInvitation() { ;
        makeResponse();
        changeStatuses();
    }

    private void makeResponse() {
        response.add(new Message("Игрок " + user.getUsername() + " отклонил ваше предложение", userOpponent));
    }

    private void changeStatuses() {
        user.setOpponent(null);
        user.setUserStatus(UserStatus.FREE);
        userOpponent.setOpponent(null);
        userOpponent.setUserStatus(UserStatus.FREE);
    }
}
