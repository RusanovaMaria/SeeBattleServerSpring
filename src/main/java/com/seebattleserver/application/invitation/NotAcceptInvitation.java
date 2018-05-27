package com.seebattleserver.application.invitation;

import com.seebattleserver.application.client.Client;
import com.seebattleserver.application.user.UserStatus;

public class NotAcceptInvitation extends Invitation {

    private Client client;

    public NotAcceptInvitation(Client client) {
        this.client = client;
    }

    @Override
    public void handleAnswer() {
        notAcceptInvitation();
    }

    private void notAcceptInvitation() {
        Client opponent = getOpponent(client);
        String message = "Игрок"+client.getName()+"отклонил ваше предложение";
        notifyOpponent(opponent, message);
        removeOpponent(client);
        changeStatus(client, UserStatus.FREE);
    }


}
