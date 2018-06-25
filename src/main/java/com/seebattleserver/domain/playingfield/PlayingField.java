package com.seebattleserver.domain.playingfield;

import com.seebattleserver.domain.cage.Cage;
import com.seebattleserver.domain.gameobject.GameObject;

import java.util.List;

public interface PlayingField {

    Cage findCage(int x, char y);

    int getWidth();

    int getHeight();

    List<GameObject> getGameObjects();

    boolean isAllObjectsDied();
}
