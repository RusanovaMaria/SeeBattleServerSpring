package com.seebattleserver.domain.gameobjectbuild;


import com.seebattleserver.domain.gameobject.Ship;

public interface GameObjectBuild {

    Ship build(int size);
}
