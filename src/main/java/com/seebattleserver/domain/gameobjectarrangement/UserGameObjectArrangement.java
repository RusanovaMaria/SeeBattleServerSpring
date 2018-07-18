package com.seebattleserver.domain.gameobjectarrangement;

import com.seebattleserver.domain.cage.Cage;
import com.seebattleserver.domain.gameobject.GameObject;
import com.seebattleserver.domain.gameobjectpart.GameObjectPart;
import com.seebattleserver.domain.playingfield.ClassicPlayingField;
import com.seebattleserver.domain.playingfield.PlayingField;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class UserGameObjectArrangement implements GameObjectArrangement {
    private PlayingField playingField = new ClassicPlayingField();
    private Map<Integer, List<List<String>>> coordinates;

    public UserGameObjectArrangement(Map<Integer, List<List<String>>> coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public PlayingField arrange() {
        Set<Integer> gameObjectSizes = coordinates.keySet();
        for (int size : gameObjectSizes) {
            List<GameObject> gameObjectsOfCurrentSize = playingField.getGameObjectsBySize(size);
            List<List<String>> coordinatesForGameObjectOfCurrentSize = coordinates.get(size);
            arrangeGameObjectsOfCurrentSize(gameObjectsOfCurrentSize, coordinatesForGameObjectOfCurrentSize);
        }
        return playingField;
    }

    private void arrangeGameObjectsOfCurrentSize(List<GameObject> gameObjectsOfCurrentSize, List<List<String>> coordinatesForGameObjectOfCurrentSize) {
        int coordinatesForCurrentGameObjectIndex = 0;
        for (GameObject gameObject : gameObjectsOfCurrentSize) {
            List<String> coordinatesForCurrentGameObject = coordinatesForGameObjectOfCurrentSize.get(coordinatesForCurrentGameObjectIndex);
            arrangeGameObject(gameObject, coordinatesForCurrentGameObject);
        }
    }

    private void arrangeGameObject(GameObject gameObject, List<String> coordinatesForCurrentGameObject) {
        List<GameObjectPart> gameObjectParts = gameObject.getGameObjectParts();
        int currentCoordinateIndex = 0;
        for (GameObjectPart gameObjectPart : gameObjectParts) {
            String coordinate = coordinatesForCurrentGameObject.get(currentCoordinateIndex);
            Cage cage = identifyCage(coordinate);
            cage.setGameObjectPart(gameObjectPart);
            currentCoordinateIndex++;
        }
    }

  /*  private void arrangeGameObjectPart(String coordinate, GameObjectPart gameObjectPart) {
        Cage cage = identifyCage(coordinate);
        cage.setGameObjectPart(gameObjectPart);
    } */

    private Cage identifyCage(String coordinate) {
        coordinate = coordinate.trim();
        int x = Character.getNumericValue(coordinate.charAt(0));
        System.out.println(x);
        char y = coordinate.charAt(1);
        System.out.println(y);
        return playingField.identifyCage(x, y);
    }
}
