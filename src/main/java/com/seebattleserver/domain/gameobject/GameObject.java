package com.seebattleserver.domain.gameobject;

import com.seebattleserver.domain.gameobjectpart.GameObjectPart;

import java.util.List;

public interface GameObject {

    void shoot();

    Kind getKind();

    int getSize();

    Status getStatus();

    List<GameObjectPart> getObjectParts();
}
