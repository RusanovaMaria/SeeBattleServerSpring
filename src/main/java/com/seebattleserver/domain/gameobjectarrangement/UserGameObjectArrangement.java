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
    private PlayingField playingField;
    private Map<Integer, List<List<String>>> coordinates;

    public UserGameObjectArrangement(Map<Integer, List<List<String>>> coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public PlayingField arrange() {
        playingField = new ClassicPlayingField();
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
            arrangeGameObjectPart(coordinate, gameObjectPart);
            currentCoordinateIndex++;
        }
    }

    private void arrangeGameObjectPart(String coordinate, GameObjectPart gameObjectPart) {
        Cage cage = identifyCage(coordinate);
        cage.setGameObjectPart(gameObjectPart);
    }

    private Cage identifyCage(String coordinate) {
        coordinate = coordinate.trim();
        int x = coordinate.charAt(0);
        char y = coordinate.charAt(1);
        return playingField.identifyCage(x, y);
    }
}
