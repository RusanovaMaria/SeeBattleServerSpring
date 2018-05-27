package com.seebattleserver.application.controller;

import com.seebattleserver.application.client.Client;
import com.seebattleserver.application.client.ClientSet;
import com.seebattleserver.application.user.User;
import com.seebattleserver.application.user.UserStatus;

public class ControllerManager {

    private User user;

    public ControllerManager(User user) {
        this.client = client;
    }

    public void handle(String command) {
        UserStatus userStatus = identifyClientStatus();
        Controller controller = identifyControllerByClientStatus(userStatus);
        controller.handle(command);
    }

        private UserStatus identifyClientStatus() {
            UserStatus userStatus = client.getStatus();
            return userStatus;
        }

        private Controller identifyControllerByClientStatus(UserStatus status) {
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

