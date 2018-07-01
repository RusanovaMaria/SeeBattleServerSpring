package com.seebattleserver.domain.rule;

import java.util.HashMap;
import java.util.Map;

public class ClassicRule implements Rule {

    private Map<Integer, Integer> quantityOfGameObjects;


    public ClassicRule() {
        initQuantityOfGameObjects();
    }

    private void initQuantityOfGameObjects() {
        quantityOfGameObjects = new HashMap();

        quantityOfGameObjects.put(1, 4);
        quantityOfGameObjects.put(2, 3);
        quantityOfGameObjects.put(3, 2);
        quantityOfGameObjects.put(4, 1);
    }

    @Override
    public int countQuantityOfObjects(int size) {
        int number = quantityOfGameObjects.get(size);
        return number;
    }
}
