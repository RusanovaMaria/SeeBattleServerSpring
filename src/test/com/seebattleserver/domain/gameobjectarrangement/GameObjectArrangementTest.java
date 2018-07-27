package com.seebattleserver.domain.gameobjectarrangement;

import com.seebattleserver.domain.cage.Cage;
import com.seebattleserver.domain.cage.State;
import com.seebattleserver.domain.coordinatecouple.CoordinatesCouple;
import com.seebattleserver.domain.gameobjectpositioncontroller.ShipInstallationController;
import com.seebattleserver.domain.playingfield.ClassicPlayingField;
import com.seebattleserver.domain.playingfield.PlayingField;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class GameObjectArrangementTest {
    private static PlayingField playingField;
    private static GameObjectArrangement gameObjectArrangement;

    @BeforeClass
    public static void setUp() {
        playingField = new ClassicPlayingField();
        gameObjectArrangement = new GameObjectArrangement(new ShipInstallationController());
    }

    @Test
    public void arrangeGameObjects_whenGameObjectCoordinatesAreValid_returnStatusCageWhenGameObjectWasInstalled() {
        int x = 5;
        char y = 'a';
        int gameObjectSize = 1;
        List<List<CoordinatesCouple>> gameObjectsOfCurrentSizeCoordinates = new ArrayList<>();
        List<CoordinatesCouple> singleGameObjectCoordinates = new ArrayList<>();
        singleGameObjectCoordinates.add(new CoordinatesCouple(x, y));
        gameObjectsOfCurrentSizeCoordinates.add(singleGameObjectCoordinates);
        Map<Integer, List<List<CoordinatesCouple>>> gameObjectsCoordinates = new HashMap<>();
        gameObjectsCoordinates.put(gameObjectSize, gameObjectsOfCurrentSizeCoordinates);
        try {
            gameObjectArrangement.arrangeGameObjects(gameObjectsCoordinates, playingField);
        } catch (Exception ex) {

        }
        Cage cage = playingField.identifyCage(x, y);
        assertEquals(State.FULL, cage.getState());
    }

    @Test
    public void arrangeGameObjects_whenGameObjectCoordinatesAreValidButNotValidCoordinatesQuantity_returnCagesStatuses() {
        int x1 = 5, x2 = 6;
        char y = 'a';
        int gameObjectSize = 1;
        List<List<CoordinatesCouple>> gameObjectsOfCurrentSizeCoordinates = new ArrayList<>();
        List<CoordinatesCouple> singleGameObjectCoordinates = new ArrayList<>();
        singleGameObjectCoordinates.add(new CoordinatesCouple(x1, y));
        singleGameObjectCoordinates.add(new CoordinatesCouple(x2, y));
        gameObjectsOfCurrentSizeCoordinates.add(singleGameObjectCoordinates);
        Map<Integer, List<List<CoordinatesCouple>>> gameObjectsCoordinates = new HashMap<>();
        gameObjectsCoordinates.put(gameObjectSize, gameObjectsOfCurrentSizeCoordinates);
        try {
            gameObjectArrangement.arrangeGameObjects(gameObjectsCoordinates, playingField);
        } catch (Exception ex) {

        }
        Cage fullCage = playingField.identifyCage(x1, y);
        Cage ignoredCage = playingField.identifyCage(x2, y);
        assertEquals(State.FULL, fullCage.getState());
        assertNotEquals(State.FULL, ignoredCage.getState());
    }

    @Test
    public void arrangeGameObjects_whenNotValidGameObjectSize_returnStatusCageWhenGameObjectShouldHaveBeenInstalled() {
        int x = 5;
        char y = 'a';
        int gameObjectSize = 7;
        List<List<CoordinatesCouple>> gameObjectsOfCurrentSizeCoordinates = new ArrayList<>();
        List<CoordinatesCouple> singleGameObjectCoordinates = new ArrayList<>();
        singleGameObjectCoordinates.add(new CoordinatesCouple(x, y));
        gameObjectsOfCurrentSizeCoordinates.add(singleGameObjectCoordinates);
        Map<Integer, List<List<CoordinatesCouple>>> gameObjectsCoordinates = new HashMap<>();
        gameObjectsCoordinates.put(gameObjectSize, gameObjectsOfCurrentSizeCoordinates);
        try {
            gameObjectArrangement.arrangeGameObjects(gameObjectsCoordinates, playingField);
        } catch (Exception ex) {

        }
        Cage cage = playingField.identifyCage(x, y);
        assertEquals(State.FREE, cage.getState());
    }

    @Test(expected = IllegalArgumentException.class)
    public void arrangeGameObjects_whenNotValidGameObjectSize_returnException() {
        int x = 5;
        char y = 'a';
        int gameObjectSize = 7;
        List<List<CoordinatesCouple>> gameObjectsOfCurrentSizeCoordinates = new ArrayList<>();
        List<CoordinatesCouple> singleGameObjectCoordinates = new ArrayList<>();
        singleGameObjectCoordinates.add(new CoordinatesCouple(x, y));
        gameObjectsOfCurrentSizeCoordinates.add(singleGameObjectCoordinates);
        Map<Integer, List<List<CoordinatesCouple>>> gameObjectsCoordinates = new HashMap<>();
        gameObjectsCoordinates.put(gameObjectSize, gameObjectsOfCurrentSizeCoordinates);
        try {
            gameObjectArrangement.arrangeGameObjects(gameObjectsCoordinates, playingField);
        } catch (Exception ex) {
            throw new IllegalArgumentException("Несуществующий размер игрового объекта");
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void arrangeGameObjects_whenGameObjectCoordinatesAreNotValid_returnException() {
        int x = 10;
        char y = 'a';
        int gameObjectSize = 1;
        List<List<CoordinatesCouple>> gameObjectsOfCurrentSizeCoordinates = new ArrayList<>();
        List<CoordinatesCouple> singleGameObjectCoordinates = new ArrayList<>();
        singleGameObjectCoordinates.add(new CoordinatesCouple(x, y));
        gameObjectsOfCurrentSizeCoordinates.add(singleGameObjectCoordinates);
        Map<Integer, List<List<CoordinatesCouple>>> gameObjectsCoordinates = new HashMap<>();
        gameObjectsCoordinates.put(gameObjectSize, gameObjectsOfCurrentSizeCoordinates);
        try {
            gameObjectArrangement.arrangeGameObjects(gameObjectsCoordinates, playingField);
        } catch (Exception ex) {
            throw new IllegalArgumentException("Не валидные координаты");
        }
    }
}