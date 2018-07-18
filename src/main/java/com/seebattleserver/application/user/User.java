package com.seebattleserver.application.user;

import com.seebattleserver.domain.player.Player;

public class User {
    private String username;
    private Player player;
    private UserStatus userStatus;
    private User userOpponent;

    public User(String username) {
        this.username = username;
        userStatus = UserStatus.FREE;
        player = new Player();
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

    public User getUserOpponent() {
        return userOpponent;
    }

    public void setUserOpponent(User userOpponent) {
        this.userOpponent = userOpponent;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
