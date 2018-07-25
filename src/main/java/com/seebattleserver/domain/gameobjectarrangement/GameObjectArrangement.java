package com.seebattleserver.domain.gameobjectarrangement;

import com.seebattleserver.domain.cage.Cage;
import com.seebattleserver.domain.gameobject.GameObject;
import com.seebattleserver.domain.gameobjectpart.GameObjectPart;
import com.seebattleserver.domain.gameobjectpositioncontroller.GameObjectPositionController;
import com.seebattleserver.domain.playingfield.PlayingField;
import com.seebattleserver.domain.rule.Rule;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.seebattleserver.domain.cage.State.PROHIBITED_USE;

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
        setCagesAroundUseCageProhibitedUse(cage);
    }

    private void setCagesAroundUseCageProhibitedUse(Cage cage) {
        Rule rule = gameObjectPositionController.getRule();
        int x = cage.getX();
        char y = cage.getY();
        setCagesAroundUseCageProhibitedUseHorizontally(rule, x, y);
        setCagesAroundUseCageProhibitedUseVertically(rule, x, y);
    }

    private void setCagesAroundUseCageProhibitedUseHorizontally(Rule rule, int x, char y) {
        List<Integer> xCoordinates = new ArrayList<>();
        if (rule.isValidIntCoordinateValue(x + 1)) {
            xCoordinates.add(x + 1);
        }
        if (rule.isValidIntCoordinateValue(x - 1)) {
            xCoordinates.add(x - 1);
        }
        for (int i = 0; i < xCoordinates.size(); i++) {
            Cage prohibitedUseCage = playingField.identifyCage(xCoordinates.get(i), y);
            prohibitedUseCage.setState(PROHIBITED_USE);
        }
    }

    private void setCagesAroundUseCageProhibitedUseVertically(Rule rule, int x, char y) {
        List<Character> yCoordinates = new ArrayList<>();
        try {
            yCoordinates.add(rule.getPreviousCharCoordinate(y));
            yCoordinates.add(rule.getNextCharCoordinate(y));
        } catch (Exception ex) {

        }
        for (int i = 0; i < yCoordinates.size(); i++) {
            Cage prohibitedUseCage = playingField.identifyCage(x, yCoordinates.get(i));
            prohibitedUseCage.setState(PROHIBITED_USE);
        }
    }

    private Cage identifyCage(String coordinatesCouple) {
        coordinatesCouple = coordinatesCouple.trim();
        int x = Character.getNumericValue(coordinatesCouple.charAt(0));
        char y = coordinatesCouple.charAt(1);
        return playingField.identifyCage(x, y);
    }
}
