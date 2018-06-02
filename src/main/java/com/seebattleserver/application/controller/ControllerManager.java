package com.seebattleserver.application.controller;

import com.seebattleserver.application.user.User;
import com.seebattleserver.application.user.UserStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ControllerManager {

   // private User user;

    @Autowired
    private ControllerFactory controllerFactory;

  /*  public ControllerManager(User user) {
        this.user = user;
    } */

    public void handle(User user ,String command) {
        UserStatus userStatus = user.getUserStatus();
        Controller controller = identifyControllerByClientStatus(user, userStatus);
        controller.handle(command);
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
            throw new IllegalArgumentException("Данный статус клиента не распознан");
        }
    }

