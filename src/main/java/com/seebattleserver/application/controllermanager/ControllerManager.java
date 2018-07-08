package com.seebattleserver.application.controller.controllermanager;

import com.seebattleserver.application.controller.Controller;
import com.seebattleserver.application.controllerfactory.ControllerFactory;
import com.seebattleserver.application.user.User;
import com.seebattleserver.application.user.UserStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ControllerManager {
    private ControllerFactory controllerFactory;

    @Autowired
    public ControllerManager(ControllerFactory controllerFactory) {
        this.controllerFactory = controllerFactory;
    }

    public void handle(User user, String message) {
        UserStatus userStatus = user.getUserStatus();
        Controller controller = identifyControllerByClientStatus(user, userStatus);
        controller.handle(message);
    }

    private Controller identifyControllerByClientStatus(User user, UserStatus status) {
        switch (status) {
            case FREE:
                return controllerFactory.createCommandController(user);
            case INVITED:
                return controllerFactory.createInvitationResponseController(user);
            case INVITING:
                return controllerFactory.createCommandController(user);
            case REQUESTING_OPPONENT:
                return controllerFactory.createInvitationController(user);
            case IN_GAME:
                return controllerFactory.createGameController(user);
            case IN_GAME_MOVE:
                return controllerFactory.createGameController(user);
        }
        throw new IllegalStateException("Данный статус клиента не распознан");
    }
}

