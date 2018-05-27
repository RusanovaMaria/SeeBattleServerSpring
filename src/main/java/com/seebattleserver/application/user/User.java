package com.seebattleserver.application.user;

import com.seebattleserver.application.controller.CommandController;
import com.seebattleserver.application.controller.Controller;
import com.seebattleserver.domain.player.Player;

public class User {

    private String username;
    private Controller controller;
    private Player player;

    public User(String username) {
        this.username = username;
        this.controller = new CommandController(this);
    }

    public String getUsername() {
        return username;
    }

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

}
