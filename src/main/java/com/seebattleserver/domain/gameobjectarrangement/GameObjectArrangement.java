package com.seebattleserver.domain.gameobjectarrangement;

import com.seebattleserver.domain.cage.Cage;
import com.seebattleserver.domain.gameobject.GameObject;
import com.seebattleserver.domain.gameobjectpart.GameObjectPart;
import com.seebattleserver.domain.gameobjectpositioncontroller.GameObjectPositionController;
import com.seebattleserver.domain.playingfield.PlayingField;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class GameObjectArrangement {
    private PlayingField playingField;
    private GameObjectPositionController gameObjectPositionController;

    public GameObjectArrangement(GameObjectPositionController gameObjectPositionController) {
        this.gameObjectPositionController = gameObjectPositionController;
    }

    public void arrangeGameObjects(Map<Integer, List<List<String>>> coordinates, PlayingField playingField) {
        this.playingField = playingField;
        Set<Integer> gameObjectSizes = coordinates.keySet();
        for (int size : gameObjectSizes) {
            List<GameObject> gameObjectsOfCurrentSize = playingField.getGameObjectsBySize(size);
            List<List<String>> coordinatesForGameObjectOfCurrentSize = coordinates.get(size);
            arrangeGameObjectsOfCurrentSize(gameObjectsOfCurrentSize, coordinatesForGameObjectOfCurrentSize);
        }
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

    private void arrangeGameObjectPart(String coordinatesCouple, GameObjectPart gameObjectPart) {
        Cage cage = identifyCage(coordinatesCouple);
        cage.setGameObjectPart(gameObjectPart);
    }

    private Cage identifyCage(String coordinatesCouple) {
        coordinatesCouple = coordinatesCouple.trim();
        int x = Character.getNumericValue(coordinatesCouple.charAt(0));
        char y = coordinatesCouple.charAt(1);
        return playingField.identifyCage(x, y);
    }
}
