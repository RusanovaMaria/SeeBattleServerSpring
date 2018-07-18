package com.seebattleserver.domain.gameobject;

import com.seebattleserver.domain.gameobjectpart.GameObjectPart;

import java.util.List;

public interface GameObject {

    int getSize();

    void setSize(int size);

    Status getStatus();

    void setStatus(Status status);

    List<GameObjectPart> getGameObjectParts();

    void setGameObjectParts(List<GameObjectPart> gameObjectParts);

    int getDiedGameObjectParts();

    void setDiedGameObjectParts(int diedGameObjectParts);

}
