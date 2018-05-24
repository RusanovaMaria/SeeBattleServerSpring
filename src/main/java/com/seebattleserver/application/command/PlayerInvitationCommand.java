package com.seebattleserver.application.command;

import com.seebattleserver.application.client.Client;
import com.seebattleserver.application.client.ClientStatus;

import java.io.IOException;

public class PlayerInvitationCommand extends Command {

    private Client client;

    public PlayerInvitationCommand(Client client) {
        this.client = client;
    }

    @Override
    public void execute() {
        try {
            client.sendMessage("Введите имя соперника");
            client.setStatus(ClientStatus.REQUESTING_OPPONENT);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
