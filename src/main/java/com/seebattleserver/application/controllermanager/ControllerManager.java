package com.seebattleserver.application.controllermanager;

import com.seebattleserver.application.controller.Controller;
import com.seebattleserver.application.controllerfactory.ControllerFactory;
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

    public void handle(User user, TextMessage textMessage) {
        UserStatus userStatus = user.getUserStatus();
        Controller controller = identifyControllerByClientStatus(user, userStatus);
        controller.handle(textMessage);
    }

    private Controller identifyControllerByClientStatus(User user, UserStatus status) {
        switch (status) {
            case REGISTERING:
                return controllerFactory.createUserRegistrationController(user);
            case FREE:
                return controllerFactory.createCommandController(user);
            case INVITED:
                return controllerFactory.createInvitationResponseController(user);
            case INVITING:
                return controllerFactory.createInvitationController(user);
            case READY_FOR_GAME:
                return controllerFactory.createGameStartController(user);
            case SET_UP_GAME_OBJECTS:
                return controllerFactory.createUserGameObjectArrangementController(user);
            case IN_GAME:
                return controllerFactory.createGameProcessController(user);
            case IN_GAME_MOVE:
                return controllerFactory.createGameProcessController(user);
        }
        throw new IllegalStateException("Данный статус клиента не распознан");
    }
}

