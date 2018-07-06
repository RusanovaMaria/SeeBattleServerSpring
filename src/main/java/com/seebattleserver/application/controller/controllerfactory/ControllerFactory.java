package com.seebattleserver.application.controller.controllerfactory;

import com.google.gson.Gson;
import com.seebattleserver.application.command.commandlist.CommandList;
import com.seebattleserver.application.controller.CommandController;
import com.seebattleserver.application.controller.GameController;
import com.seebattleserver.application.controller.InvitationResponseController;
import com.seebattleserver.application.controller.InvitationController;
import com.seebattleserver.application.gameregistry.GameRegistry;
import com.seebattleserver.application.user.User;
import com.seebattleserver.application.user.UserRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ControllerFactory {
    private GameRegistry gameRegistry;
    private UserRegistry userRegistry;
    private CommandList commandList;
    private Gson gson;

    @Autowired
    public ControllerFactory(GameRegistry gameRegistry,
                             UserRegistry userRegistry, CommandList commandList, Gson gson) {
        this.gameRegistry = gameRegistry;
        this.userRegistry = userRegistry;
        this.commandList = commandList;
        this.gson = gson;
    }

    public CommandController createCommandController(User user) {
        return new CommandController(user, commandList ,gson);
    }

    public GameController createGameController(User user) {
        return new GameController(user, gameRegistry, gson);
    }

    public InvitationResponseController createInvitationController(User user) {
        return new InvitationResponseController(user, gameRegistry, gson);
    }

    public InvitationController createRequestOpponentController(User user) {
        return new InvitationController(user, userRegistry, gson);
    }

}
