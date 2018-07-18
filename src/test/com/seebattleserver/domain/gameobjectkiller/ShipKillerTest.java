package com.seebattleserver.domain.gameobjectkiller;

import com.seebattleserver.domain.gameobject.GameObject;
import com.seebattleserver.domain.gameobject.Ship;
import com.seebattleserver.domain.gameobject.Status;
import com.seebattleserver.domain.gameobjectbuild.GameObjectBuilder;
import com.seebattleserver.domain.gameobjectbuild.ShipBuilder;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ShipKillerTest {
    private GameObject ship;
    private GameObjectBuilder gameObjectBuilder;

    @Before
    public void setUp() {
        gameObjectBuilder = new ShipBuilder();
        ship = gameObjectBuilder.build(2);
    }

    @Test
    public void killPart_whenKillGameObject_returnGameObjectStatus() {
        GameObject ship = gameObjectBuilder.build(1);
        GameObjectKiller gameObjectKiller = new ShipKiller(ship);
        gameObjectKiller.killPart();
        gameObjectKiller.killPart();
        Status result = ship.getStatus();
        assertEquals(Status.KILLED, result);
    }

    @Test
    public void killPart_whenDamageGameObject_returnGameObjectStatus() {
        GameObject ship = gameObjectBuilder.build(2);
        GameObjectKiller gameObjectKiller = new ShipKiller(ship);
        gameObjectKiller.killPart();
        Status result = ship.getStatus();
        assertEquals(Status.DAMAGED, result);
    }
}