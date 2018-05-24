package com.seebattleserver.domain.playingfield;

import com.seebattleserver.domain.cage.Cage;

public interface PlayingField {

    Cage findCage(int x, char y);

    int getWidth();

    int getHeight();

    char[] getCharCoordinate();
}
