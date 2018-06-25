package com.seebattleserver.application.adding_game_objects;

public class StandardCoordinate implements Coordinate{

    private final static char[] ALLOWED_CHAR_COORDINATES = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j'};

    private int x;
    private char y;

    StandardCoordinate(int x, char y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public char getY() {
        return y;
    }

    public static char[] getAllowedCharCoordinates() {
        return ALLOWED_CHAR_COORDINATES;
    }
}
