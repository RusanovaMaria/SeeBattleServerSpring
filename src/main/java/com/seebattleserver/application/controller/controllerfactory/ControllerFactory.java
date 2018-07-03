package com.seebattleserver.application.controller.controllerfactory;

import com.google.gson.Gson;
import com.seebattleserver.application.command.commandlist.CommandList;
import com.seebattleserver.application.controller.CommandController;
import com.seebattleserver.application.controller.GameController;
import com.seebattleserver.application.controller.InvitationController;
import com.seebattleserver.application.controller.RequestOpponentController;
import com.seebattleserver.application.gameregistry.GameRegistry;
import com.seebattleserver.application.user.User;
import com.seebattleserver.application.user.UserRegistry;
import com.seebattleserver.service.sender.UserSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ControllerFactory {

    private UserSender userSender;
    private GameRegistry gameRegistry;
    private UserRegistry userRegistry;
    private CommandList commandList;
    private Gson gson;

    @Autowired
    public ControllerFactory(UserSender userSender, GameRegistry gameRegistry,
                             UserRegistry userRegistry, CommandList commandList, Gson gson) {
        this.userSender = userSender;
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

    public InvitationController createInvitationController(User user) {
        return new InvitationController(user, gameRegistry, gson);
    }

    public RequestOpponentController createRequestOpponentController(User user) {
        return new RequestOpponentController(user, userRegistry, userSender, gson);
    }

}
