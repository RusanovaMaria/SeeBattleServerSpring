package com.seebattleserver.application.controller;

import com.google.gson.Gson;
import com.seebattleserver.application.command.CommandFactory;
import com.seebattleserver.application.gameregistry.GameRegistry;
import com.seebattleserver.application.user.User;
import com.seebattleserver.application.user.UserRegistry;
import com.seebattleserver.service.sender.UserSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ControllerFactory {

    private UserSender userSender;
    private CommandFactory commandFactory;
    private GameRegistry gameRegistry;
    private UserRegistry userRegistry;
    private Gson gson;

    @Autowired
    public ControllerFactory(UserSender userSender, CommandFactory commandFactory, GameRegistry gameRegistry,
                             UserRegistry userRegistry, Gson gson) {
        this.userSender = userSender;
        this.commandFactory = commandFactory;
        this.gameRegistry = gameRegistry;
        this.userRegistry = userRegistry;
        this.gson = gson;
    }

    public CommandController createCommandController(User user) {
        return new CommandController(user, userSender, commandFactory, gson);
    }

    public GameController createGameController(User user) {
        return new GameController(user, userSender, gameRegistry, gson);
    }

    public InvitationController createInvitationController(User user) {
        return new InvitationController(user, userSender, gameRegistry, gson);
    }

    public RequestOpponentController createRequestOpponentController(User user) {
        return new RequestOpponentController(user, userRegistry, userSender, gson);
    }

}
