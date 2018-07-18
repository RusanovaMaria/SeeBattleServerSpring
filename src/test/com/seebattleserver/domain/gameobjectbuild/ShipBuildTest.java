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
        GameObjectBuilder gameObjectBuilder = new ShipBuilder();
        GameObject ship = gameObjectBuilder.build(3);
        List<GameObjectPart> gameObjectParts = ship.getGameObjectParts();
        assertNotNull(gameObjectParts);
    }

    @Test (expected = IllegalArgumentException.class)
    public void build_whenGameObjectSizeIsNotValid_returnException() {
        GameObjectBuilder gameObjectBuilder = new ShipBuilder();
        GameObject ship = gameObjectBuilder.build(5);
    }
}