package com.seebattleserver.domain.gameobjectpositioncontroller;

import com.seebattleserver.domain.cage.Cage;
import com.seebattleserver.domain.coordinatecouple.CoordinatesCouple;
import com.seebattleserver.domain.gameobject.GameObject;
import com.seebattleserver.domain.playingfield.PlayingField;
import com.seebattleserver.domain.rule.ClassicRule;
import com.seebattleserver.domain.rule.Rule;

import java.util.List;

import static com.seebattleserver.domain.cage.State.FREE;

public class ShipInstallationController implements GameObjectInstallationController {
    private Rule rule;

    public ShipInstallationController() {
        rule = new ClassicRule();
    }

    @Override
    public boolean gameObjectCanBeInstalled(List<CoordinatesCouple> shipCoordinates, PlayingField playingField) {
        int size = shipCoordinates.size();
        if ((isNotAllShipsOfCurrentSizeInstalled(size, playingField)) && (isValidPlace(shipCoordinates, playingField)) &&
                (isValidSequenceOfCoordinates(shipCoordinates))) {
            return true;
        }
        return false;
    }

    private boolean isNotAllShipsOfCurrentSizeInstalled(int size, PlayingField playingField) {
        if (!isAllShipsOfCurrentSizeInstalled(size, playingField)) {
            return true;
        }
        return false;
    }

    private boolean isAllShipsOfCurrentSizeInstalled(int size, PlayingField playingField) {
        List<GameObject> gameObjectsOfCurrentSize = playingField.getGameObjectsBySize(size);
        for (GameObject gameObject : gameObjectsOfCurrentSize) {
            if (!gameObject.wasInstalled()) {
                return false;
            }
        }
        return true;
    }

    private boolean isValidPlace(List<CoordinatesCouple> shipCoordinates, PlayingField playingField) {
        for (CoordinatesCouple coordinatesCouple : shipCoordinates) {
            int x = coordinatesCouple.getX();
            char y = coordinatesCouple.getY();
            Cage cage = playingField.identifyCage(x, y);
            if (!canBeUsed(cage)) {
                return false;
            }
        }
        return true;
    }

    private boolean canBeUsed(Cage cage) {
        if (cage.getState() == FREE) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isValidSequenceOfCoordinates(List<CoordinatesCouple> shipCoordinates) {
        if ((isValidSequenceOfHorizontalCoordinates(shipCoordinates)) || (isValidSequenceOfVerticalCoordinates(shipCoordinates))) {
            return true;
        }
        return false;
    }

    public boolean isValidSequenceOfVerticalCoordinates(List<CoordinatesCouple> shipCoordinates) {
        if (!isVerticalArrangement(shipCoordinates)) {
            return false;
        }
        for (int i = 0; i < shipCoordinates.size() - 1; i++) {
            CoordinatesCouple coordinatesCouple = shipCoordinates.get(i);
            char y = coordinatesCouple.getY();
            CoordinatesCouple nextCoordinatesCouple = shipCoordinates.get(i + 1);
            char yNext = nextCoordinatesCouple.getY();
            if (rule.getNextCharCoordinate(y) != yNext) {
                return false;
            }
        }
        return true;
    }

    private boolean isVerticalArrangement(List<CoordinatesCouple> shipCoordinates) {
        if (shipCoordinates.size() == 1) {
            return true;
        }
        CoordinatesCouple firstCoordinatesCouple = shipCoordinates.get(0);
        int x = firstCoordinatesCouple.getX();
        for (CoordinatesCouple coordinateCouple : shipCoordinates) {
            if (coordinateCouple.getX() != x) {
                return false;
            }
        }
        return true;
    }

    private boolean isValidSequenceOfHorizontalCoordinates(List<CoordinatesCouple> shipCoordinates) {
        if (!isHorizontalArrangement(shipCoordinates)) {
            return false;
        }
        for (int i = 0; i < shipCoordinates.size() - 1; i++) {
            CoordinatesCouple coordinatesCouple = shipCoordinates.get(i);
            int x = coordinatesCouple.getX();
            CoordinatesCouple nextCoordinatesCouple = shipCoordinates.get(i + 1);
            int xNext = nextCoordinatesCouple.getX();
            if (x + 1 != xNext) {
                return false;
            }
        }
        return true;
    }

    private boolean isHorizontalArrangement(List<CoordinatesCouple> shipCoordinates) {
        if (shipCoordinates.size() == 1) {
            return true;
        }
        CoordinatesCouple firstCoordinatesCouple = shipCoordinates.get(0);
        char y = firstCoordinatesCouple.getY();
        for (CoordinatesCouple coordinateCouple : shipCoordinates) {
            if (coordinateCouple.getY() != y) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Rule getRule() {
        return rule;
    }
}
