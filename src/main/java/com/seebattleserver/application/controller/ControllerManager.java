package com.seebattleserver.application.controller;

import com.seebattleserver.application.client.Client;
import com.seebattleserver.application.client.ClientSet;
import com.seebattleserver.application.client.ClientStatus;

public class ControllerManager {

    private Client client;
    private ClientSet clientSet = new ClientSet();

    public ControllerManager(Client client) {
        this.client = client;
    }

        public void handle(String command) {
        ClientStatus clientStatus = identifyClientStatus();
        Controller controller = identifyControllerByClientStatus(clientStatus);
        controller.handle(command);
        }

        private ClientStatus identifyClientStatus() {
            ClientStatus clientStatus = client.getStatus();
            return clientStatus;
        }

        private Controller identifyControllerByClientStatus(ClientStatus status) {
            switch (status) {
                case FREE:
                    return new CommandController(client);
                case INVITED:
                    return new InvitationController(client);
                case IN_GAME:
                    return new GameController(client);
                case REQUESTING_OPPONENT:
                    return new RequestOpponentController(client);
            }
            throw new IllegalArgumentException("Данный статус клиента не распознан");
        }
    }

