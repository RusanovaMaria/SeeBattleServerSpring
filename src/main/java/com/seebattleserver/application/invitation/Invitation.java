package com.seebattleserver.application.invitation;

import com.seebattleserver.application.client.Client;
import com.seebattleserver.application.client.ClientSet;
import com.seebattleserver.application.client.ClientStatus;

import java.io.IOException;

public abstract class Invitation {

    protected ClientSet clientSet = new ClientSet();

    public abstract void handleAnswer();

    protected void changeStatus(Client client, ClientStatus status) {
        client.setStatus(status);
    }

    protected void removeOpponent(Client client) {
        client.removeOpponent();
    }

    protected void notifyOpponent(Client opponent, String message){
        try {
            opponent.sendMessage(message);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    protected Client getOpponent(Client client) {
        return client.getOpponent();
    }
}
