package domain.playingfield;

import domain.cage.Cage;

public interface PlayingField {

    Cage findCage(int x, char y);

    int getWidth();

    int getHeight();

    char[] getCharCoordinate();
}
