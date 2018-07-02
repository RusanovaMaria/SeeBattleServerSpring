package com.seebattleserver.domain.gameobjectbuild;

import com.seebattleserver.domain.gameobject.GameObject;
import com.seebattleserver.domain.gameobjectpart.GameObjectPart;
import com.seebattleserver.domain.gameobjectpart.ShipPart;

import java.util.ArrayList;
import java.util.List;

public class ShipBuild implements GameObjectBuild {
    private static final int MIN_SHIP_SIZE = 1;
    private static final int MAX_SHIP_SIZE = 4;
    private GameObject ship;
    private List<GameObjectPart> gameObjectParts;
    private int size;

    public ShipBuild(GameObject ship) {
        this.ship = ship;
        gameObjectParts = new ArrayList<>();
        size = ship.getSize();
    }

    @Override
    public void build() {
        if (isValidGameObjectSize()) {
            generateGameObjectParts();
            ship.setGameObjectParts(gameObjectParts);
        } else {
            throw new IllegalArgumentException("Недопустимый размер корабля");
        }
    }

    private boolean isValidGameObjectSize() {
        if ((size >= MIN_SHIP_SIZE) && (size <= MAX_SHIP_SIZE)) {
            return true;
        }
        return false;
    }

    private void generateGameObjectParts() {
        gameObjectParts = new ArrayList();
        for (int i = 0; i < size; i++) {
            gameObjectParts.add(new ShipPart(ship));
        }
    }
}
