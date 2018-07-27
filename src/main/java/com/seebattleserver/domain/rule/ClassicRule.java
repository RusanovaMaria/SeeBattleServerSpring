package com.seebattleserver.domain.rule;

import java.util.HashMap;
import java.util.Map;

public class ClassicRule implements Rule {
    private static final int MIN_GAME_OBJECT_SIZE = 1;
    private static final int MAX_GAME_OBJECT_SIZE = 4;
    private static final int[] ALLOWED_GAME_OBJECT_SIZES = {1, 2, 3, 4};
    private static final char[] CHAR_COORDINATES = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j'};
    private static final int MIN_INT_COORDINATE_VALUE = 0;
    private static final int MAX_INT_COORDINATE_VALUE = 9;
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
    public int getMaxGameObjectSize() {
        return MAX_GAME_OBJECT_SIZE;
    }

    @Override
    public int[] getAllowedGameObjectSizes() {
        return ALLOWED_GAME_OBJECT_SIZES;
    }

    @Override
    public char[] charCoordinates() {
        return CHAR_COORDINATES;
    }

    @Override
    public boolean isValidCharCoordinate(char y) {
        for (char coordinate : CHAR_COORDINATES) {
            if(coordinate == y) {
                return true;
            }
        }
        return false;
    }

    public boolean isValidIntCoordinate(int x) {
        if((x >= MIN_INT_COORDINATE_VALUE) && (x <= MAX_INT_COORDINATE_VALUE)) {
            return true;
        }
        return false;
    }

    @Override
    public char getNextCharCoordinate(char y) {
        int yCoordinateIndex;
        try {
            yCoordinateIndex = getCharCoordinateIndex(y);
            return CHAR_COORDINATES[yCoordinateIndex + 1];
        } catch (Exception ex) {
            throw new IllegalArgumentException("Следующего значения у не существует");
        }
    }

    @Override
    public char getPreviousCharCoordinate(char y) {
        int yCoordinateIndex;
        try {
            yCoordinateIndex = getCharCoordinateIndex(y);
            return CHAR_COORDINATES[yCoordinateIndex - 1];
        } catch (Exception ex) {
            throw new IllegalArgumentException("Предыдущего значения у не существует");
        }
    }

    private int getCharCoordinateIndex(char y) {
        for(int i = 0; i < CHAR_COORDINATES.length; i++) {
            if(CHAR_COORDINATES[i] == y) {
                return i;
            }
        }
        throw new IllegalArgumentException("Данной координаты не существует");
    }
}
