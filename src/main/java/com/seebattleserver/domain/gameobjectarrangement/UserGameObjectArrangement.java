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
        int singleGameObjectCoordinatesIndex = 0;
        for (GameObject gameObject : gameObjectsOfCurrentSize) {
            List<String> coordinatesForSingleGameObject = coordinatesForGameObjectOfCurrentSize.get(singleGameObjectCoordinatesIndex);
            arrangeGameObject(gameObject, coordinatesForSingleGameObject);
            singleGameObjectCoordinatesIndex++;
        }
    }

    private void arrangeGameObject(GameObject gameObject, List<String> coordinatesForCurrentGameObject) {
        List<GameObjectPart> gameObjectParts = gameObject.getGameObjectParts();
        int coordinatesCoupleIndex = 0;
        for (GameObjectPart gameObjectPart : gameObjectParts) {
            String coordinatesCouple = coordinatesForCurrentGameObject.get(coordinatesCoupleIndex);
            arrangeGameObjectPart(coordinatesCouple, gameObjectPart);
            coordinatesCoupleIndex++;
        }
        gameObject.install();
    }

    private void arrangeGameObjectPart(String coordinate, GameObjectPart gameObjectPart) {
        Cage cage = identifyCage(coordinate);
        cage.setGameObjectPart(gameObjectPart);
    }

    private Cage identifyCage(String coordinatesCouple) {
        coordinatesCouple = coordinatesCouple.trim();
        int x = Character.getNumericValue(coordinatesCouple.charAt(0));
        char y = coordinatesCouple.charAt(1);
        return playingField.identifyCage(x, y);
    }
}
