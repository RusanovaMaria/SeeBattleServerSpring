package domain.rule;

import domain.gameobject.Kind;

public interface Rule {

    int[] getGameObjectsSize();

    int countQuantityOfObjects(Kind kind);
}
