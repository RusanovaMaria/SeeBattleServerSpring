package com.seebattleserver.application.command;

import com.seebattleserver.application.client.Client;

import java.io.IOException;
import java.util.List;

public class PlayerListCommand extends Command {

    private Client client;

    public PlayerListCommand(Client client) {
        this.client = client;
    }

    @Override
    public void execute() {
        writeOpponentList();
    }

    private void writeOpponentList() {
        List<Client> opponents = clientSet.getClients();
        for (int i = 0; i < opponents.size(); i++) {
                Client opponent = opponents.get(i);
                writeOpponentName(opponent);
        }
    }

    private void writeOpponentName(Client opponent) {
        try {
            client.sendMessage(opponent.getName());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
