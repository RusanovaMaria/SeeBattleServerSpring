package com.seebattleserver.application.controllerfactory;

import com.seebattleserver.application.controller.commandcontroller.CommandController;
import com.seebattleserver.application.controller.commandcontroller.CommandList;
import com.seebattleserver.application.controller.gameprocesscontroller.GameController;
import com.seebattleserver.application.controller.gamestartcontroller.GameStartController;
import com.seebattleserver.application.controller.invitationresponsecontroller.InvitationResponseController;
import com.seebattleserver.application.controller.invitationcontroller.InvitationController;
import com.seebattleserver.application.controller.usergameobjectarrangementcontroller.UserGameObjectArrangementController;
import com.seebattleserver.application.gameregistry.GameRegistry;
import com.seebattleserver.application.user.User;
import com.seebattleserver.application.user.UserRegistry;
import com.seebattleserver.service.sender.UserSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ControllerFactory {
    private GameRegistry gameRegistry;
    private UserRegistry userRegistry;
    private CommandList commandList;
    private UserSender userSender;

    @Autowired
    public ControllerFactory(GameRegistry gameRegistry,
                             UserRegistry userRegistry,
                             CommandList commandList,
                             UserSender userSender) {
        this.gameRegistry = gameRegistry;
        this.userRegistry = userRegistry;
        this.commandList = commandList;
        this.userSender = userSender;
    }

    public CommandController createCommandController(User user) {
        return new CommandController(user, commandList, userSender);
    }

    public GameController createGameProcessController(User user) {
        return new GameController(user, gameRegistry, userSender);
    }

    public InvitationController createInvitationController(User user) {
        return new InvitationController(user, userRegistry, userSender);
    }

    public InvitationResponseController createInvitationResponseController(User user) {
        return new InvitationResponseController(user, gameRegistry, userSender);
    }

    public GameStartController createGameStartController(User user) {
        return new GameStartController(user, userSender, gameRegistry);
    }

    public UserGameObjectArrangementController createUserGameObjectArrangementController() {
        return new UserGameObjectArrangementController();
    }
}
