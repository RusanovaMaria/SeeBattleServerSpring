package com.seebattleserver.domain.gameobject;

public interface GameObject {

    void shoot();

    Kind getKind();

    int getSize();

    Status getStatus();
}
