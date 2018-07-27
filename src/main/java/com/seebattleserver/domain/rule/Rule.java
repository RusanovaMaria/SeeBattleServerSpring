package com.seebattleserver.domain.rule;

public interface Rule {

    int countQuantityOfObjects(int size);

    boolean isValidGameObjectSize(int size);

    int getMaxGameObjectSize();

    int[] getAllowedGameObjectSizes();

    char[] charCoordinates();

    boolean isValidCharCoordinate(char y);

    boolean isValidIntCoordinate(int x);

    char getNextCharCoordinate(char y);

    char getPreviousCharCoordinate(char y);
}
