package com.seebattleserver.domain.gameobjectaddition;

import com.seebattleserver.domain.gameobject.GameObject;
import com.seebattleserver.domain.gameobjectbuild.ShipBuilder;
import com.seebattleserver.domain.playingfield.PlayingField;
import com.seebattleserver.domain.rule.ClassicRule;
import com.seebattleserver.domain.rule.Rule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassicGameObjectAddition implements GameObjectAddition {
    private Rule rule;

    public ClassicGameObjectAddition() {
        rule = new ClassicRule();
    }

    @Override
    public Map<Integer, List<GameObject>> addGameObjects(PlayingField playingField) {
        Map<Integer, List<GameObject>> gameObjects = new HashMap<>();
        for (int i = 0; i < rule.getMaxGameObjectSize(); i++) {
            int size = rule.getAllowedGameObjectSizes()[i];
            List<GameObject> gameObjectsOfCurrentSize = addGameObjectsBySize(size);
            gameObjects.put(size, gameObjectsOfCurrentSize);
        }
        return gameObjects;
    }

    private List<GameObject> addGameObjectsBySize(int size) {
        List<GameObject> gameObjectsOfCurrentSize = new ArrayList<>();
        int gameObjectQuantity = rule.countQuantityOfObjects(size);
        for (int j = 0; j < gameObjectQuantity; j++) {
            ShipBuilder shipBuilder = new ShipBuilder();
            GameObject ship = shipBuilder.build(size);
            gameObjectsOfCurrentSize.add(ship);
        }
        return gameObjectsOfCurrentSize;
    }
}
