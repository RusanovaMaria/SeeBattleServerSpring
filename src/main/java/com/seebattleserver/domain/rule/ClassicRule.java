package com.seebattleserver.domain.rule;

import com.seebattleserver.domain.gameobject.Kind;

import java.util.HashMap;
import java.util.Map;

public class ClassicRule implements Rule {

    private final int[] gameObjectsSize = {1, 2, 3, 4};
    private Map<Kind, Integer> quantityOfGameObjects;


    public ClassicRule() {
        initQuantityOfGameObjects();
    }

    @Override
    public int[] getGameObjectsSize() {
        return gameObjectsSize;
    }

    @Override
    public int countQuantityOfObjects(Kind kind) {

        int number = quantityOfGameObjects.get(kind);
        return number;
    }

    private void initQuantityOfGameObjects() {
        quantityOfGameObjects = new HashMap();

        quantityOfGameObjects.put(Kind.SINGLE_DECKED, 4);
        quantityOfGameObjects.put(Kind.DOUBLE_DECKED, 3);
        quantityOfGameObjects.put(Kind.THREE_DECKED, 2);
        quantityOfGameObjects.put(Kind.FOUR_DECKED, 1);
    }
}
