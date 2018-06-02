package com.seebattleserver.application.controller;

import com.seebattleserver.application.command.CommandFactory;
import com.seebattleserver.application.gameset.GameRegistry;
import com.seebattleserver.application.user.User;
import com.seebattleserver.application.user.UserRegistry;
import com.seebattleserver.service.sender.UserSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ControllerFactory {

    private User user;
    private UserSender userSender;

    @Autowired
    CommandFactory commandFactory;

    @Autowired
    GameRegistry gameRegistry;

    @Autowired
    UserRegistry userRegistry;


    public CommandController createCommandController(User user) {
        return new CommandController(user, userSender, commandFactory);
    }

    public GameController createGameController(User user) {
        return new GameController(user, userSender, gameRegistry);
    }

    public InvitationController createInvitationController(User user) {
        return new InvitationController(user, userSender, gameRegistry);
    }

    public RequestOpponentController createRequestOpponentController(User user) {
        return new RequestOpponentController(user, userRegistry, userSender);
    }

}
