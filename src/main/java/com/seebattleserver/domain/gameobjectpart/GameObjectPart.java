package com.seebattleserver.domain.gameobjectpart;

import com.seebattleserver.domain.gameobject.GameObject;

public interface GameObjectPart {

    GameObject getGameObject();

    boolean isAlive();

    void kill();
}
