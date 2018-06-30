package com.seebattleserver.domain.ship;

import com.seebattleserver.domain.gameobject.GameObject;
import com.seebattleserver.domain.gameobject.Ship;
import com.seebattleserver.domain.gameobject.Status;
import junit.framework.TestCase;
import org.junit.Test;

public class ShipTest extends TestCase {

    @Test
    public void testShoot_whenShipIsKilled_returnStatusKilled() {
        GameObject ship = new Ship(1);
        ship.killPart();
        Status result = ship.getStatus();
        assertEquals(Status.KILLED, result);
    }

    @Test
    public void testShoot_whenShipIsDamaged_returnStatusDamaged() {
        GameObject ship = new Ship(4);
        ship.killPart();
        Status result = ship.getStatus();
        assertEquals(Status.DAMAGED, result);
    }

    @Test
    public void testGetStatus_whenShipIsNew_returnStatusAlive(){
        GameObject ship = new Ship(4);
        Status result = ship.getStatus();
        assertEquals(Status.ALIVE, result);
    }
}