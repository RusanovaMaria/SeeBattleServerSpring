package com.seebattleserver.domain.gameobjectbuild;

import com.seebattleserver.domain.gameobject.Ship;
import com.seebattleserver.domain.gameobjectpart.GameObjectPart;
import com.seebattleserver.domain.gameobjectpart.ShipPart;
import com.seebattleserver.domain.rule.ClassicRule;
import com.seebattleserver.domain.rule.Rule;

import java.util.ArrayList;
import java.util.List;

public class ShipBuild implements GameObjectBuild {
    private Rule rule;

    public ShipBuild() {
        rule = new ClassicRule();
    }

    @Override
    public Ship build(int size) {
        if (rule.isValidGameObjectSize(size)) {
            Ship ship = buildShip(size);
            return ship;
        } else {
            throw new IllegalArgumentException("Недопустимый размер корабля");
        }
    }

    private Ship buildShip(int size) {
        Ship ship = new Ship();
        ship.setSize(size);
        List<GameObjectPart> gameObjectParts = generateGameObjectParts(size, ship);
        ship.setGameObjectParts(gameObjectParts);
        return ship;
    }

    private List<GameObjectPart> generateGameObjectParts(int size, Ship ship) {
        List<GameObjectPart> gameObjectParts = new ArrayList();
        for (int i = 0; i < size; i++) {
            gameObjectParts.add(new ShipPart(ship));
        }
        return gameObjectParts;
    }
}
