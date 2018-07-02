package com.seebattleserver.domain.cage;

import com.seebattleserver.domain.gameobjectpart.GameObjectPart;
import com.seebattleserver.domain.gameobjectpart.ShipPart;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CageTest{
    private Cage cage;

    @Before
    public void setUp() {
        cage = new Cage(1, 'a', null);
    }

    @Test
    public void testMarkAsUsed_returnUsedState() {
        cage.markAsUsed();
        State result = cage.getState();
        assertEquals(State.USED, result);
    }
    @Test
    public void testSetGameObjectPart_whenGameObjectPartIsNotNull_returnFullState() {
        GameObjectPart gameObjectPart = new ShipPart(null);
        cage.setGameObjectPart(gameObjectPart);
        State result = cage.getState();
        assertEquals(State.FULL, result);
    }
    @Test
    public void testSetGameObjectPart_whenGameObjectPartIsNull_returnFreeState() {
        cage.setGameObjectPart(null);
        State result = cage.getState();
        assertEquals(State.FREE, result);
    }
}