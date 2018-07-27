package com.seebattleserver.domain.playingfield;

import com.seebattleserver.domain.cage.Cage;
import com.seebattleserver.domain.gameobject.GameObject;
import com.seebattleserver.domain.gameobject.Status;
import com.seebattleserver.domain.gameobjectarrangement.defaultgameobjectarrangement.ClassicDefaultGameObjectArrangement;
import com.seebattleserver.domain.gameobjectarrangement.defaultgameobjectarrangement.DefaultGameObjectArrangement;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ClassicPlayingFieldTest {
    private PlayingField playingField;

    @Before
    public  void setUp() {
        playingField = new ClassicPlayingField();
    }

    @Test
    public void identifyCage_whenCoordinatesAreValid_returnCageWithValidCoordinates() {
        Cage cage = playingField.identifyCage(1, 'a');
        assertEquals(1, cage.getX());
        assertEquals('a', cage.getY());
    }

    @Test (expected = IllegalArgumentException.class)
    public void identifyCage_whenCoordinatesAreNotValid_returnException() {
        Cage cage = playingField.identifyCage(11, 'a');
    }

    @Test
    public void isAllObjectsDied_whenNotAllObjectsDied_returnFalse() {
        boolean result = playingField.allGameObjectsDied();
        assertFalse(result);
    }

    @Test
    public void isAllObjectsDied_whenAllObjectsDied_returnTrue() {
        killAllObjects();
        boolean result = playingField.allGameObjectsDied();
        assertTrue(result);
    }

    private void killAllObjects() {
        for (int size = 1; size <= 4; size++) {
            List<GameObject> gameObjects = playingField.getGameObjectsBySize(size);
            for (int i = 0; i < gameObjects.size(); i++) {
                GameObject gameObject = gameObjects.get(i);
                gameObject.setStatus(Status.KILLED);
            }
        }
    }

    @Test
    public void getGameObjectsBySize_whenGameObjectSizeIs3_returnListWithGameObjectSize() {
        List<GameObject> gameObjects = playingField.getGameObjectsBySize(3);
        int result = gameObjects.size();
        assertEquals(2, result);
    }

    @Test
    public void allGameObjectsInstalled_whenGameObjectsAreNotInstalled_returnFalse() {
        assertFalse(playingField.allGameObjectsInstalled());
    }

    @Test
    public void allGameObjectsInstalled_whenNotAllGameObjectsAreInstalled_returnFalse() {
        installSomeGameObjects();
        assertFalse(playingField.allGameObjectsInstalled());
    }

    private void installSomeGameObjects() {
        List<GameObject> gameObjectsOfCurrentSize = playingField.getGameObjectsBySize(1);
        for(GameObject gameObject : gameObjectsOfCurrentSize) {
            gameObject.install();
        }
    }

    @Test
    public void allGameObjectsInstalled_whenAllGameObjectsAreInstalled_returnTrue() {
        installAllGameObjects();
        assertTrue(playingField.allGameObjectsInstalled());
    }

    private void installAllGameObjects() {
        DefaultGameObjectArrangement defaultGameObjectArrangement = new ClassicDefaultGameObjectArrangement();
        defaultGameObjectArrangement.arrangeGameObjectsByDefault(playingField);
    }
}