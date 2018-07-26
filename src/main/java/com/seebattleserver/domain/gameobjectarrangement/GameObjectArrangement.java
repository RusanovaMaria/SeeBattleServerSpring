package com.seebattleserver.domain.gameobjectarrangement;

import com.seebattleserver.domain.cage.Cage;
import com.seebattleserver.domain.coordinatecouple.CoordinatesCouple;
import com.seebattleserver.domain.gameobject.GameObject;
import com.seebattleserver.domain.gameobjectpart.GameObjectPart;
import com.seebattleserver.domain.gameobjectpositioncontroller.GameObjectInstallationController;
import com.seebattleserver.domain.playingfield.PlayingField;
import com.seebattleserver.domain.rule.Rule;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.seebattleserver.domain.cage.State.PROHIBITED_USE;

public class GameObjectArrangement {
    private PlayingField playingField;
    private GameObjectInstallationController gameObjectInstallationController;

    public GameObjectArrangement(GameObjectInstallationController gameObjectInstallationController) {
        this.gameObjectInstallationController = gameObjectInstallationController;
    }

    public void arrangeGameObjects(Map<Integer, List<List<CoordinatesCouple>>> coordinates, PlayingField playingField)
    throws Exception{
        this.playingField = playingField;
        Set<Integer> gameObjectSizes = coordinates.keySet();
        for (int size : gameObjectSizes) {
            List<GameObject> gameObjectsOfCurrentSize = playingField.getGameObjectsBySize(size);
            List<List<CoordinatesCouple>> coordinatesForGameObjectsOfCurrentSize = coordinates.get(size);
            try {
                arrangeGameObjectsOfCurrentSize(gameObjectsOfCurrentSize, coordinatesForGameObjectsOfCurrentSize);
            }catch (Exception ex) {
                throw new IllegalArgumentException("Игровые объекты не могут быть размещены");
            }
        }
    }

    private void arrangeGameObjectsOfCurrentSize(List<GameObject> gameObjectsOfCurrentSize, List<List<CoordinatesCouple>> coordinatesForGameObjectsOfCurrentSize)
    throws Exception{
        int singleGameObjectCoordinatesIndex = 0;
        for (GameObject gameObject : gameObjectsOfCurrentSize) {
            List<CoordinatesCouple> coordinatesForSingleGameObject = coordinatesForGameObjectsOfCurrentSize.get(singleGameObjectCoordinatesIndex);
            arrangeGameObject(gameObject, coordinatesForSingleGameObject);
            singleGameObjectCoordinatesIndex++;
        }
    }

    private void arrangeGameObject(GameObject gameObject, List<CoordinatesCouple> coordinatesForSingleGameObject) {
        if (gameObjectInstallationController.gameObjectCanBeInstalled(coordinatesForSingleGameObject, playingField)) {
            List<GameObjectPart> gameObjectParts = gameObject.getGameObjectParts();
            int coordinatesCoupleIndex = 0;
            for (GameObjectPart gameObjectPart : gameObjectParts) {
                CoordinatesCouple coordinatesCouple = coordinatesForSingleGameObject.get(coordinatesCoupleIndex);
                arrangeGameObjectPart(coordinatesCouple, gameObjectPart);
                coordinatesCoupleIndex++;
            }
            gameObject.install();
        } else {
            throw new IllegalArgumentException("Игровой объект не может быть установлен");
        }
    }

    private void arrangeGameObjectPart(CoordinatesCouple coordinatesCouple, GameObjectPart gameObjectPart) {
        Cage cage = identifyCage(coordinatesCouple);
        cage.setGameObjectPart(gameObjectPart);
        setCagesAroundUseCageProhibitedUse(cage);
    }

    private void setCagesAroundUseCageProhibitedUse(Cage cage) {
        Rule rule = gameObjectInstallationController.getRule();
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

    private Cage identifyCage(CoordinatesCouple coordinatesCouple) {
        int x = coordinatesCouple.getX();
        char y = coordinatesCouple.getY();
        return playingField.identifyCage(x, y);
    }
}
