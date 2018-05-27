package com.seebattleserver.application.invitation;

import com.seebattleserver.application.client.Client;
import com.seebattleserver.application.user.UserStatus;
import com.seebattleserver.application.gameimplementation.GameImplementation;

public class AcceptInvitation extends Invitation {

    private Client client;

    public AcceptInvitation(Client client) {
        this.client = client;
    }

    @Override
    public void handleAnswer(){
        acceptInvitation();
    }

    private void acceptInvitation() {
        Client opponent = getOpponent(client);
        String message = "Игрок "+client.getName()+" принял ваше предложение";
        notifyOpponent(opponent, message);
        changeStatus(client, UserStatus.IN_GAME);
        changeStatus(opponent, UserStatus.IN_GAME);
        startGame(client, opponent);
    }

    private void startGame(Client client, Client opponent) {
        GameImplementation gameImplementation = new GameImplementation(client, opponent);

        Thread t = new Thread(gameImplementation);
        t.start();
    }
}
