package com.seebattleserver.domain.gameobjectbuild;

import com.seebattleserver.domain.gameobject.GameObject;
import com.seebattleserver.domain.gameobjectpart.GameObjectPart;
import com.seebattleserver.domain.gameobjectpart.ShipPart;
import com.seebattleserver.domain.rule.ClassicRule;
import com.seebattleserver.domain.rule.Rule;

import java.util.ArrayList;
import java.util.List;

public class ShipBuild implements GameObjectBuild {
    private Rule rule;
    private GameObject ship;
    private List<GameObjectPart> gameObjectParts;
    private int size;

    public ShipBuild(GameObject ship) {
        this.ship = ship;
        gameObjectParts = new ArrayList<>();
        size = ship.getSize();
        rule = new ClassicRule();
    }

    @Override
    public void build() {
        if (rule.isValidGameObjectSize(size)) {
            generateGameObjectParts();
            ship.setGameObjectParts(gameObjectParts);
        } else {
            throw new IllegalArgumentException("Недопустимый размер корабля");
        }
    }

    private void generateGameObjectParts() {
        gameObjectParts = new ArrayList();
        for (int i = 0; i < size; i++) {
            gameObjectParts.add(new ShipPart(ship));
        }
    }
}
