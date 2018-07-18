package com.seebattleserver.domain.gameobjectbuild;


import com.seebattleserver.domain.gameobject.Ship;

public interface GameObjectBuilder {

    Ship build(int size);
}
