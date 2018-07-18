package com.seebattleserver.repository;

import com.seebattleserver.application.user.UserStatus;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class UserEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    private String name;

    private int victory_col;

    private int loss_col;

    private UserStatus status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getVictory_col() {
        return victory_col;
    }

    public void setVictory_col(int victory_col) {
        this.victory_col = victory_col;
    }

    public int getLoss_col() {
        return loss_col;
    }

    public void setLoss_col(int loss_col) {
        this.loss_col = loss_col;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }
}
