package com.seebattleserver.domain.playingfield;

import com.seebattleserver.domain.cage.Cage;
import com.seebattleserver.domain.cage.State;
import com.seebattleserver.domain.gameobject.GameObject;
import com.seebattleserver.domain.gameobject.Ship;
import com.seebattleserver.domain.gameobject.Status;
import com.seebattleserver.domain.gameobjectposition.GameObjectPosition;
import com.seebattleserver.domain.rule.ClassicRule;
import com.seebattleserver.domain.rule.Rule;
import java.util.ArrayList;
import java.util.List;

public class ClassicPlayingField implements PlayingField {

    private final char[] CHAR_COORDINATE = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j'};
    private final int WIDTH = 10;
    private final int HEIGHT = 10;
    private List<GameObject> ships;
    private Cage[][] cages;
    private GameObjectPosition gameObjectPosition;


    public ClassicPlayingField(GameObjectPosition gameObjectPosition) {
        generate();
        addGameObjects();
        this.gameObjectPosition = gameObjectPosition;
        gameObjectPosition.establish(this);
    }

    private void generate() {
        cages = new Cage[WIDTH][HEIGHT];

        for (int j = 0; j < HEIGHT; j++) {
        for (int i = 0; i < WIDTH; i++) {
                cages[i][j] = new Cage(i, CHAR_COORDINATE[j], null);
            }
        }
    }

    private void addGameObjects() {
        Rule rule = new ClassicRule();
        int[] shipsSize = rule.getGameObjectsSize();
        ships = new ArrayList();

        for (int i = 0; i < /*shipsSize.length*/1; i++) {
            int size = shipsSize[i];
            GameObject ship = new Ship(size);
            int number = rule.countQuantityOfObjects(ship.getKind());
            for (int j = 0; j < number; j++) {
                ships.add(ship);
            }
        }
    }

    @Override
    public Cage findCage(int x, char y) {
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
    public int getWidth() {
        return WIDTH;
    }

    @Override
    public int getHeight() {
        return HEIGHT;
    }

    @Override
    public char[] getCharCoordinate() {
        return CHAR_COORDINATE;
    }

    @Override
    public List<GameObject> getGameObjects() {
        return ships;
    }

    @Override
    public boolean isNoObjects() {
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                if (cages[i][j].getState() == State.FULL) {
                    return false;
                }
            }
        }
        return true;
    }
}
