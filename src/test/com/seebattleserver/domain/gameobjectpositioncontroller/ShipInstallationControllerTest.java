package com.seebattleserver.domain.gameobjectpositioncontroller;

import com.seebattleserver.domain.coordinatecouple.CoordinatesCouple;
import com.seebattleserver.domain.playingfield.ClassicPlayingField;
import com.seebattleserver.domain.playingfield.PlayingField;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ShipInstallationControllerTest {
    private static GameObjectInstallationController gameObjectInstallationController;
    private static PlayingField playingField;

    @BeforeClass
    public static void setUp() {
        gameObjectInstallationController = new ShipInstallationController();
        playingField = new ClassicPlayingField();
    }

    @Test
    public void gameObjectCanBeInstalled_whenCoordinatesAreValid_returnTrue() {
        List<CoordinatesCouple> shipCoordinates = new ArrayList<>();
        shipCoordinates.add(new CoordinatesCouple(5, 'b'));
        boolean result = gameObjectInstallationController.gameObjectCanBeInstalled(shipCoordinates, playingField);
        assertTrue(result);
    }

    @Test
    public void gameObjectCanBeInstalled_whenCoordinatesAreNotValid_returnFalse() {
        List<CoordinatesCouple> shipCoordinates = new ArrayList<>();
        shipCoordinates.add(new CoordinatesCouple(10, 'z'));
        boolean result = gameObjectInstallationController.gameObjectCanBeInstalled(shipCoordinates, playingField);
        assertFalse(result);
    }

    @Test
    public void gameObjectCanBeInstalled_whenCoordinatesAreValidButCoordinatesQuantityAreNotValid_returnFalse() {
        List<CoordinatesCouple> shipCoordinates = new ArrayList<>();
        shipCoordinates.add(new CoordinatesCouple(5, 'b'));
        shipCoordinates.add(new CoordinatesCouple(6, 'b'));
        shipCoordinates.add(new CoordinatesCouple(7, 'b'));
        shipCoordinates.add(new CoordinatesCouple(8, 'b'));
        shipCoordinates.add(new CoordinatesCouple(9, 'b'));
        boolean result = gameObjectInstallationController.gameObjectCanBeInstalled(shipCoordinates, playingField);
        assertFalse(result);
    }

    @Test
    public void gameObjectCanBeInstalled_whenCoordinatesAreValidButHorizontalCoordinatesSequenceAreNotValid_returnFalse() {
        List<CoordinatesCouple> shipCoordinates = new ArrayList<>();
        shipCoordinates.add(new CoordinatesCouple(5, 'b'));
        shipCoordinates.add(new CoordinatesCouple(6, 'f'));
        shipCoordinates.add(new CoordinatesCouple(7, 'g'));
        shipCoordinates.add(new CoordinatesCouple(8, 'a'));
        boolean result = gameObjectInstallationController.gameObjectCanBeInstalled(shipCoordinates, playingField);
        assertFalse(result);
    }

    @Test
    public void gameObjectCanBeInstalled_whenCoordinatesAreValidButVerticalCoordinatesSequenceAreNotValid_returnFalse() {
        List<CoordinatesCouple> shipCoordinates = new ArrayList<>();
        shipCoordinates.add(new CoordinatesCouple(5, 'a'));
        shipCoordinates.add(new CoordinatesCouple(8, 'a'));
        shipCoordinates.add(new CoordinatesCouple(7, 'a'));
        shipCoordinates.add(new CoordinatesCouple(6, 'a'));
        boolean result = gameObjectInstallationController.gameObjectCanBeInstalled(shipCoordinates, playingField);
        assertFalse(result);
    }

    @Test
    public void gameObjectCanBeInstalled_whenCoordinatesAreValidButHorizontalAndVerticalCoordinatesAreRepeated_returnFalse() {
        List<CoordinatesCouple> shipCoordinates = new ArrayList<>();
        shipCoordinates.add(new CoordinatesCouple(5, 'a'));
        shipCoordinates.add(new CoordinatesCouple(5, 'a'));
        shipCoordinates.add(new CoordinatesCouple(5, 'a'));
        shipCoordinates.add(new CoordinatesCouple(5, 'a'));
        boolean result = gameObjectInstallationController.gameObjectCanBeInstalled(shipCoordinates, playingField);
        assertFalse(result);
    }
}