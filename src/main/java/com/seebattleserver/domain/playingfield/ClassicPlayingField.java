package com.seebattleserver.domain.playingfield;

import com.seebattleserver.domain.cage.Cage;
import com.seebattleserver.domain.gameobject.GameObject;
import com.seebattleserver.domain.gameobject.Status;
import com.seebattleserver.domain.gameobjectaddition.GameObjectAddition;
import com.seebattleserver.domain.gameobjectaddition.ClassicGameObjectAddition;
import com.seebattleserver.domain.rule.ClassicRule;
import com.seebattleserver.domain.rule.Rule;

import java.util.List;
import java.util.Map;

public class ClassicPlayingField implements PlayingField {
    private final int WIDTH = 10;
    private final int HEIGHT = 10;
    private Map<Integer, List<GameObject>> gameObjects;
    private Cage[][] cages;
    private GameObjectAddition gameObjectAddition;
    private Rule rule;

    public ClassicPlayingField() {
        rule = new ClassicRule();
        gameObjectAddition = new ClassicGameObjectAddition();
        gameObjects = gameObjectAddition.addGameObjects(this);
        createEmptyField();
    }

    private void createEmptyField() {
        cages = new Cage[WIDTH][HEIGHT];
            for (int i = 0; i < HEIGHT; i++) {
                for (int j = 0; j < WIDTH; j++) {
                cages[i][j] = new Cage(i, rule.charCoordinates()[j], null);
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
        throw new IllegalArgumentException("Клетка с данными координатами не найдена");
    }

    @Override
    public boolean allGameObjectsDied() {
        for (List<GameObject> gameObjectsBySize : gameObjects.values()) {
            for (GameObject gameObject : gameObjectsBySize) {
                if (gameObjectIsNotKilled(gameObject)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean gameObjectIsNotKilled(GameObject gameObject) {
        if(!gameObjectIsKilled(gameObject)) {
            return true;
        }
        return false;
    }

    private boolean gameObjectIsKilled(GameObject gameObject) {
        if (gameObject.getStatus() == Status.KILLED) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<GameObject> getGameObjectsBySize(int size) {
        if (gameObjects.containsKey(size)) {
            return gameObjects.get(size);
        } else {
            throw new IllegalArgumentException("Игровых объектов заданного размера на данном игровом поле не существует");
        }
    }

    @Override
    public boolean allGameObjectsInstalled() {
        for (List<GameObject> gameObjectsBySize : gameObjects.values()) {
            for (GameObject gameObject : gameObjectsBySize) {
                if (!gameObject.wasInstalled()) {
                    return false;
                }
            }
        }
        return true;
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
