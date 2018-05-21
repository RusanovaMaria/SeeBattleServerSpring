package domain.cage;

import domain.gameobjectpart.GameObjectPart;

public class Cage {

    private boolean wasUsed;
    private char y;
    private int x;
    private GameObjectPart gameObjectPart;

    public Cage(int x, char y, GameObjectPart gameObjectPart) {

        this.y = y;
        this.x = x;
        this.gameObjectPart = gameObjectPart;
        wasUsed = false;
    }

    public void markAsUsed() {
        wasUsed = true;
    }

    public State determineState() {

        if (wasUsed) {
            return State.USED;

        } else if (gameObjectPart == null) {
            return State.FREE;

        } else return State.FULL;
    }

    public int getX() {
        return x;
    }

    public char getY() {
        return y;
    }

    public GameObjectPart getGameObjectPart() {
        return gameObjectPart;
    }
}
