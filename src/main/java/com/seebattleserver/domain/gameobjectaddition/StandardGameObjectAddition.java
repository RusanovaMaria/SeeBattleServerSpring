package com.seebattleserver.domain.gameobjectaddition;

import com.seebattleserver.domain.gameobject.GameObject;
import com.seebattleserver.domain.gameobject.Ship;
import com.seebattleserver.domain.gameobjectbuild.ShipBuild;
import com.seebattleserver.domain.playingfield.PlayingField;
import com.seebattleserver.domain.rule.ClassicRule;
import com.seebattleserver.domain.rule.Rule;

import java.util.ArrayList;
import java.util.List;

public class StandardGameObjectAddition implements GameObjectAddition {

    private static final int[] ALLOWED_GAME_OBJECT_SIZES = {1, 2, 3, 4};
    private PlayingField playingField;
    private List<GameObject> gameObjects;
    private Rule rule;

    public StandardGameObjectAddition(PlayingField playingField) {
        this.playingField = playingField;
        gameObjects = new ArrayList();
        rule = new ClassicRule();
    }

    @Override
    public void add() {
        for (int i = 0; i < ALLOWED_GAME_OBJECT_SIZES.length; i++) {
            int size = ALLOWED_GAME_OBJECT_SIZES[i];
            addGameObjectsBySize(size);
        }
        playingField.setGameObjects(gameObjects);
    }

    private void addGameObjectsBySize(int size) {
        int gameObjectQuantity = rule.countQuantityOfObjects(size);
        for (int j = 0; j < gameObjectQuantity; j++) {
            GameObject ship = new Ship(size);
            ShipBuild shipBuild = new ShipBuild(ship);
            shipBuild.build();
            gameObjects.add(ship);
        }
    }
}
