package com.seebattleserver.application.controller.controllermanager;

import com.seebattleserver.application.controller.Controller;
import com.seebattleserver.application.controller.controllerfactory.ControllerFactory;
import com.seebattleserver.application.message.Message;
import com.seebattleserver.application.responsesender.ResponseSender;
import com.seebattleserver.application.user.User;
import com.seebattleserver.application.user.UserStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;

import java.util.List;

@Component
public class ControllerManager {
    private ControllerFactory controllerFactory;
    private ResponseSender responseSender;

    @Autowired
    public ControllerManager(ControllerFactory controllerFactory, ResponseSender responseSender) {
        this.controllerFactory = controllerFactory;
        this.responseSender = responseSender;
    }

    public void handle(User user , TextMessage text) {
        UserStatus userStatus = user.getUserStatus();
        Controller controller = identifyControllerByClientStatus(user, userStatus);
        List<Message> response = controller.handle(text);
        responseSender.sendResponse(response);
    }

        private Controller identifyControllerByClientStatus(User user, UserStatus status) {
            switch (status) {
                case FREE:
                    return controllerFactory.createCommandController(user);
                case INVITED:
                    return controllerFactory.createInvitationResponseController(user);
                case INVITING:
                    return  controllerFactory.createInvitationController(user);
                case IN_GAME:
                    return controllerFactory.createGameController(user);
                case IN_GAME_MOVE:
                    return controllerFactory.createGameController(user);
               // case REQUESTING_OPPONENT:
                    //return controllerFactory.createInvitationController(user);
            }
            throw new IllegalStateException("Данный статус клиента не распознан");
        }
    }

