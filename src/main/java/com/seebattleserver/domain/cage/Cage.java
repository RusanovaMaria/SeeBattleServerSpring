package com.seebattleserver.domain.cage;

import com.seebattleserver.domain.gameobjectpart.GameObjectPart;

public class Cage {

    private char y;
    private int x;
    private GameObjectPart gameObjectPart;
    private State state;

    public Cage(int x, char y, GameObjectPart gameObjectPart) {

        this.y = y;
        this.x = x;
        this.gameObjectPart = gameObjectPart;
        this.state = State.FREE;
    }

    public void markAsUsed() {
        this.state = State.USED;
    }

    public int getX() {
        return x;
    }

    public char getY() {
        return y;
    }

    public GameObjectPart getGameObjectPart() {
        return gameObjectPart;
    }

    public void setGameObjectPart(GameObjectPart gameObjectPart) {
        if (gameObjectPart != null) {
            this.gameObjectPart = gameObjectPart;
            this.state = State.FULL;
        }
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
