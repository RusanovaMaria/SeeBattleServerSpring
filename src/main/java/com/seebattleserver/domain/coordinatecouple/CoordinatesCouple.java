package com.seebattleserver.domain.coordinatecouple;

public class CoordinatesCouple {
    private int x;
    private char y;

    public CoordinatesCouple(int x, char y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public char getY() {
        return y;
    }
}
