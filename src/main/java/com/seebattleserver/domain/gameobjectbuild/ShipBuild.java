package com.seebattleserver.domain.gameobjectbuild;

import com.seebattleserver.domain.gameobject.GameObject;
import com.seebattleserver.domain.gameobject.Kind;
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
        defineVariety(size);
    }

    public void build() {
        gameObjectParts = new ArrayList();
        for (int i = 0; i < size; i++) {
            gameObjectParts.add(new ShipPart(ship));
        }
        ship.setGameObjectParts(gameObjectParts);
    }

    private void defineVariety(int size) {
        switch (size) {
            case 1:
               ship.setKind(Kind.SINGLE_DECKED);
                break;
            case 2:
                ship.setKind(Kind.DOUBLE_DECKED);
                break;
            case 3:
                ship.setKind(Kind.THREE_DECKED);
                break;
            case 4:
                ship.setKind(Kind.SINGLE_DECKED);
                break;
            default:
                throw new IllegalArgumentException("Размер корабля не может превышать 4");
        }
    }
}
