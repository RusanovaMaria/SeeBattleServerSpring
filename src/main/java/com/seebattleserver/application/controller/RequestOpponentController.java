package com.seebattleserver.application.controller;

import com.seebattleserver.application.client.Client;
import com.seebattleserver.application.client.ClientSet;
import com.seebattleserver.application.client.ClientStatus;

import java.io.IOException;
import java.util.List;

public class RequestOpponentController implements Controller {

    private Client client;
    private ClientSet clientSet = new ClientSet();

    public RequestOpponentController(Client client) {
        this.client = client;
    }

    @Override
    public void handle(String message) {
        makeInvitation(message);
    }

    private void makeInvitation(String opponentName) {
        Client opponent = findOpponentByName(opponentName);
        if (isOpponentFree(opponent)) {
            invite(client, opponent);
        } else {
            try {
                client.sendMessage("Соперник с таким именем не найден или не может принять приглашение");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private Client findOpponentByName(String name) {
        List<Client> opponents = clientSet.getClients();
        for (int i = 0; i< opponents.size(); i++) {
            Client opponent = opponents.get(i);
            if (isOpponentName(opponent, name)) {
                return opponent;
            }
        }
        throw new IllegalArgumentException("Игрока с таким именем не существует");
    }

    private boolean isOpponentName(Client opponent, String name) {
        if (opponent.getName().equals(name)) {
            return true;
        }
        return false;
    }

    private boolean isOpponentFree(Client opponent) {
        if (opponent.getStatus().equals(ClientStatus.FREE)) {
            return true;
        }
        return false;
    }

    private void invite (Client client, Client opponent) {
        opponent.setStatus(ClientStatus.INVITED);
        client.setStatus(ClientStatus.INVITING);
        sendInvitationToOpponent(opponent, client);

        createOpponents(client, opponent);
    }

    private void createOpponents (Client client, Client opponent) {
        client.setOpponent(opponent);
        opponent.setOpponent(client);
    }

    private void sendInvitationToOpponent(Client opponent, Client client) {
      try {
          opponent.sendMessage("С вами хочет играть" + client.getName() + "Введите команду 'yes' или 'no'");
      } catch (IOException ex) {
          ex.printStackTrace();
    }
}
}
