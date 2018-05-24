package com.seebattleserver.domain.rule;

import com.seebattleserver.domain.gameobject.Kind;

public interface Rule {

    int[] getGameObjectsSize();

    int countQuantityOfObjects(Kind kind);
}
