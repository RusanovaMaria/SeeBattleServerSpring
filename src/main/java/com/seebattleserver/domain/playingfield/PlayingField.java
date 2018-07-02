package com.seebattleserver.domain.playingfield;

import com.seebattleserver.domain.cage.Cage;
import com.seebattleserver.domain.gameobject.GameObject;

import java.util.List;
import java.util.Map;

public interface PlayingField {

    Cage identifyCage(int x, char y);

    int getWidth();

    int getHeight();

    List<GameObject> getGameObjectsBySize(int size);

    void setGameObjects(Map<Integer, List<GameObject>> gameObjects);

    boolean isAllObjectsDied();
}
