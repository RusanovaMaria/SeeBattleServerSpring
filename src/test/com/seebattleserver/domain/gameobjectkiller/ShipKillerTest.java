package com.seebattleserver.domain.gameobjectkiller;

import com.seebattleserver.domain.gameobject.GameObject;
import com.seebattleserver.domain.gameobject.Ship;
import com.seebattleserver.domain.gameobject.Status;
import com.seebattleserver.domain.gameobjectbuild.GameObjectBuild;
import com.seebattleserver.domain.gameobjectbuild.ShipBuild;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ShipKillerTest {
    private GameObject ship;

    @Before
    public void setUp() {
        ship = new Ship(2);
        GameObjectBuild shipBuild = new ShipBuild(ship);
        shipBuild.build();
    }

    @Test
    public void killPart_whenKillGameObject_returnGameObjectStatus() {
        GameObject ship = new Ship(1);
        GameObjectBuild shipBuild = new ShipBuild(ship);
        shipBuild.build();
        GameObjectKiller gameObjectKiller = new ShipKiller(ship);
        gameObjectKiller.killPart();
        gameObjectKiller.killPart();
        Status result = ship.getStatus();
        assertEquals(Status.KILLED, result);
    }

    @Test
    public void killPart_whenDamageGameObject_returnGameObjectStatus() {
        GameObject ship = new Ship(2);
        GameObjectBuild shipBuild = new ShipBuild(ship);
        shipBuild.build();
        GameObjectKiller gameObjectKiller = new ShipKiller(ship);
        gameObjectKiller.killPart();
        Status result = ship.getStatus();
        assertEquals(Status.DAMAGED, result);
    }
}