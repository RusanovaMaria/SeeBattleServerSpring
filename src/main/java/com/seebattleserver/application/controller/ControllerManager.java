package com.seebattleserver.application.controller;

import com.seebattleserver.application.user.User;
import com.seebattleserver.application.user.UserStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;

@Component
public class ControllerManager {

    private ControllerFactory controllerFactory;

    @Autowired
    public ControllerManager(ControllerFactory controllerFactory) {
        this.controllerFactory = controllerFactory;
    }

    public void handle(User user , TextMessage text) {
        UserStatus userStatus = user.getUserStatus();
        Controller controller = identifyControllerByClientStatus(user, userStatus);
        controller.handle(text);
    }

        private Controller identifyControllerByClientStatus(User user, UserStatus status) {
            switch (status) {
                case FREE:
                    return controllerFactory.createCommandController(user);
                case INVITED:
                    return controllerFactory.createInvitationController(user);
                case INVITING:
                    return controllerFactory.createCommandController(user);
                case IN_GAME:
                    return controllerFactory.createGameController(user);
                case IN_GAME_MOVE:
                    return controllerFactory.createGameController(user);
                case REQUESTING_OPPONENT:
                    return controllerFactory.createRequestOpponentController(user);
            }
            throw new IllegalStateException("Данный статус клиента не распознан");
        }
    }

