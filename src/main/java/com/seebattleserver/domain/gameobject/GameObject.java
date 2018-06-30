package com.seebattleserver.domain.gameobject;

import com.seebattleserver.domain.gameobjectpart.GameObjectPart;

import java.util.List;

public interface GameObject {

    Kind getKind();

    void setKind(Kind kind);

    int getSize();

    Status getStatus();

    void setStatus(Status status);

    List<GameObjectPart> getGameObjectParts();

    void setGameObjectParts(List<GameObjectPart> gameObjectParts);

    int getDiedGameObjectParts();

    void setDiedGameObjectParts(int diedGameObjectParts);

}
