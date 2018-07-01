package com.seebattleserver.domain.gameobjectbuild;

import com.seebattleserver.domain.gameobject.GameObject;
import com.seebattleserver.domain.gameobjectpart.GameObjectPart;
import com.seebattleserver.domain.gameobjectpart.ShipPart;

import java.util.ArrayList;
import java.util.List;

public class ShipBuild implements GameObjectBuild {

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
        gameObjectParts = new ArrayList();
        for (int i = 0; i < size; i++) {
            gameObjectParts.add(new ShipPart(ship));
        }
        ship.setGameObjectParts(gameObjectParts);
    }
}
