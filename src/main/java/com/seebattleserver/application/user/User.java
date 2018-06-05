package com.seebattleserver.application.user;

import com.seebattleserver.application.controller.Controller;
import com.seebattleserver.domain.player.Player;

public class User {

    private String username;
    private Player player;
    private UserStatus userStatus;
    private User opponent;

    public User(String username) {
        this.username = username;
        this.userStatus = UserStatus.FREE;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserStatus getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }

    public User getOpponent() {
        return opponent;
    }

    public void setOpponent(User opponent) {
        this.opponent = opponent;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
