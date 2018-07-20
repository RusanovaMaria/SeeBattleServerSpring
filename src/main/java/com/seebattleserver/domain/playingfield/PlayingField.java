package com.seebattleserver.domain.playingfield;

import com.seebattleserver.domain.cage.Cage;
import com.seebattleserver.domain.gameobject.GameObject;

import java.util.List;
import java.util.Map;

public interface PlayingField {

    Cage identifyCage(int x, char y);

    boolean isAllGameObjectsDied();

    List<GameObject> getGameObjectsBySize(int size);

    boolean isAllGameObjectsInstalled();

    int getWidth();

    int getHeight();
}
