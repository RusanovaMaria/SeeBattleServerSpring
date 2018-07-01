package com.seebattleserver.domain.gameobject;

import com.seebattleserver.domain.gameobjectpart.GameObjectPart;

import java.util.List;

public class Ship implements GameObject {

    private int size;
    private Status status;
    private List<GameObjectPart> gameObjectParts;
    private Kind kind;
    private int diedGameObjectParts;

    public Ship(int size) {
        this.size = size;
        status = Status.ALIVE;
    }

    public void setKind(Kind kind) {
        this.kind = kind;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public Status getStatus() {
        return status;
    }

    @Override
    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public List<GameObjectPart> getGameObjectParts() {
        return gameObjectParts;
    }

    @Override
    public void setGameObjectParts(List<GameObjectPart> gameObjectParts) {
        this.gameObjectParts = gameObjectParts;
    }

    @Override
    public int getDiedGameObjectParts() {
        return diedGameObjectParts;
    }

    @Override
    public void setDiedGameObjectParts(int diedGameObjectParts) {
        this.diedGameObjectParts = diedGameObjectParts;
    }
}
