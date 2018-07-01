package com.seebattleserver.domain.playingfield;

import com.seebattleserver.application.adding_game_objects.StandardCoordinate;
import com.seebattleserver.domain.cage.Cage;
import com.seebattleserver.domain.gameobject.GameObject;
import com.seebattleserver.domain.gameobject.Status;
import com.seebattleserver.domain.gameobjectposition.GameObjectPosition;

import java.util.List;

public class ClassicPlayingField implements PlayingField {

    private final int WIDTH = 10;
    private final int HEIGHT = 10;
    private List<GameObject> ships;
    private Cage[][] cages;


    public ClassicPlayingField(GameObjectPosition gameObjectPosition) {
        createEmptyField();
    }

    private void createEmptyField() {
        cages = new Cage[WIDTH][HEIGHT];

        for (int j = 0; j < HEIGHT; j++) {
            for (int i = 0; i < WIDTH; i++) {
                cages[i][j] = new Cage(i, StandardCoordinate.getAllowedCharCoordinates()[j], null);
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
        for (int i = 0; i < ships.size(); i++) {
            GameObject gameObject = ships.get(i);
            if (gameObject.getStatus() != Status.KILLED) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void setGameObjectParts(List<GameObject> ships) {
        this.ships = ships;
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
