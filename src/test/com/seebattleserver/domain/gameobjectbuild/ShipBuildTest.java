package com.seebattleserver.domain.gameobjectbuild;

import com.seebattleserver.domain.gameobject.GameObject;
import com.seebattleserver.domain.gameobject.Ship;
import com.seebattleserver.domain.gameobjectpart.GameObjectPart;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ShipBuildTest {

    @Test
    public void build_whenGameObjectSizeIsValid_returnGameObjectPArtsIsNotNull() {
        GameObject ship = new Ship(3);
        GameObjectBuild gameObjectBuild = new ShipBuild(ship);
        gameObjectBuild.build();
        List<GameObjectPart> gameObjectParts = ship.getGameObjectParts();
        assertNotNull(gameObjectParts);
    }

    @Test (expected = IllegalArgumentException.class)
    public void build_whenGameObjectSizeIsNotValid_returnException() {
        GameObject ship = new Ship(5);
        GameObjectBuild gameObjectBuild = new ShipBuild(ship);
        gameObjectBuild.build();
    }
}