package com.seebattleserver.domain.playingfield;

import com.seebattleserver.domain.cage.Cage;
import com.seebattleserver.domain.gameobject.GameObject;
import com.seebattleserver.domain.gameobject.Status;
import com.seebattleserver.domain.gameobjectaddition.GameObjectAddition;
import com.seebattleserver.domain.gameobjectaddition.StandardGameObjectAddition;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class ClassicPlayingField implements PlayingField {
    private final static char[] CHAR_COORDINATES = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j'};
    private final int WIDTH = 10;
    private final int HEIGHT = 10;
    private Map<Integer, List<GameObject>> gameObjects;
    private Cage[][] cages;


    public ClassicPlayingField() {
        createEmptyField();
        GameObjectAddition gameObjectAddition = new StandardGameObjectAddition(this);
        gameObjectAddition.add();
    }

    private void createEmptyField() {
        cages = new Cage[WIDTH][HEIGHT];

        for (int j = 0; j < HEIGHT; j++) {
            for (int i = 0; i < WIDTH; i++) {
                cages[i][j] = new Cage(i, CHAR_COORDINATES[j], null);
            }
        }
    }

    @Override
    public Cage identifyCage(int x, char y) {
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                if ((cages[i][j].getX() == x) && (cages[i][j].getY() == y)) {
                    return cages[i][j];
                }
            }
        }
        throw new IllegalArgumentException("Клетка с данными координаами не найдена");
    }

    @Override
    public boolean isAllObjectsDied() {
        Set<Integer> gameObjectSize = gameObjects.keySet();
        for (int size : gameObjectSize) {
            if (isNotAllObjectsOfCurrentSizeDied(size)) {
                return false;
            }
        }
        return true;
    }

    private boolean isNotAllObjectsOfCurrentSizeDied(int size) {
        List<GameObject> gameObjectOfCurrentSize = gameObjects.get(size);
        for (int i = 0; i < gameObjectOfCurrentSize.size(); i++) {
            GameObject gameObject = gameObjectOfCurrentSize.get(i);
            if (gameObject.getStatus() != Status.KILLED) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<GameObject> getGameObjects(int size) {
        return gameObjects.get(size);
    }

    @Override
    public void setGameObjects(Map<Integer, List<GameObject>> gameObjects) {
        this.gameObjects = gameObjects;
    }

    @Override
    public int getWidth() {
        return WIDTH;
    }

    @Override
    public int getHeight() {
        return HEIGHT;
    }
}
