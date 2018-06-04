package domain.shippart;

import com.seebattleserver.domain.gameobject.Ship;
import com.seebattleserver.domain.gameobjectpart.GameObjectPart;
import com.seebattleserver.domain.gameobjectpart.ShipPart;
import junit.framework.TestCase;

public class ShipPartTest extends TestCase {

    public void testKill_whenKillShipPart_returnIsAliveFalse() {
        GameObjectPart shipPart = new ShipPart(new Ship(2));
        shipPart.kill();
        boolean result = shipPart.isAlive();
        assertFalse(result);
    }

    public void testIsAlive_whenShipPartIsAlive_returnTrue() {
        GameObjectPart shipPart = new ShipPart(new Ship(2));
        boolean result = shipPart.isAlive();
        assertTrue(result);
    }

    public void testIsAlive_whenShipPartIsKilled_returnFalse() {
        GameObjectPart shipPart = new ShipPart(new Ship(2));
        shipPart.kill();
        boolean result = shipPart.isAlive();
        assertFalse(result);
    }
}