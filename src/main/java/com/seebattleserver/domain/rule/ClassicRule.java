package com.seebattleserver.domain.rule;

import java.util.HashMap;
import java.util.Map;

public class ClassicRule implements Rule {
    private static final int MIN_GAME_OBJECT_SIZE = 1;
    private static final int MAX_GAME_OBJECT_SIZE = 4;
    private static final int[] ALLOWED_GAME_OBJECT_SIZES = {1, 2, 3, 4};
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
        if (isValidGameObjectSize(size)) {
            int number = quantityOfGameObjects.get(size);
            return number;
        }
        throw new IllegalArgumentException("Недопустимый размер игрового объекта");
    }

    @Override
    public boolean isValidGameObjectSize(int size) {
        if ((size >= MIN_GAME_OBJECT_SIZE) && (size <= MAX_GAME_OBJECT_SIZE)) {
            return true;
        }
        return false;
    }

    @Override
    public int getMinGameObjectSize() {
        return MIN_GAME_OBJECT_SIZE;
    }

    @Override
    public int getMaxGameObjectSize() {
        return MAX_GAME_OBJECT_SIZE;
    }

    @Override
    public int[] getAllowedGameObjectSizes() {
        return ALLOWED_GAME_OBJECT_SIZES;
    }
}
