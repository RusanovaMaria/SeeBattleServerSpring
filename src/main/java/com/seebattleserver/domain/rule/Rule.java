package com.seebattleserver.domain.rule;

public interface Rule {

    int countQuantityOfObjects(int size);

    int getMinGameObjectSize();

    boolean isValidGameObjectSize(int size);

    int getMaxGameObjectSize();

    int[] getAllowedGameObjectSizes();
}
