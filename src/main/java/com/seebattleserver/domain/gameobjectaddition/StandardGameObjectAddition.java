package com.seebattleserver.domain.gameobjectaddition;

import com.seebattleserver.domain.gameobject.GameObject;
import com.seebattleserver.domain.gameobject.Ship;
import com.seebattleserver.domain.gameobjectbuild.ShipBuild;
import com.seebattleserver.domain.playingfield.PlayingField;
import com.seebattleserver.domain.rule.ClassicRule;
import com.seebattleserver.domain.rule.Rule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StandardGameObjectAddition implements GameObjectAddition {
    private PlayingField playingField;
    private Map<Integer, List<GameObject>> gameObjects;
    private Rule rule;

    public StandardGameObjectAddition(PlayingField playingField) {
        this.playingField = playingField;
        gameObjects = new HashMap<>();
        rule = new ClassicRule();
    }

    @Override
    public void add() {
        for (int i = 0; i < rule.getMaxGameObjectSize(); i++) {
            int size = rule.getAllowedGameObjectSizes()[i];
            addGameObjectsBySize(size);
        }
        playingField.setGameObjects(gameObjects);
    }

    private void addGameObjectsBySize(int size) {
        List<GameObject> gameObjectsOfCurrentSize = new ArrayList<>();
        int gameObjectQuantity = rule.countQuantityOfObjects(size);
        for (int j = 0; j < gameObjectQuantity; j++) {
            GameObject ship = new Ship(size);
            ShipBuild shipBuild = new ShipBuild(ship);
            shipBuild.build();
            gameObjectsOfCurrentSize.add(ship);
        }
        gameObjects.put(size, gameObjectsOfCurrentSize);
    }
}
