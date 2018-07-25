package com.seebattleserver.domain.gameobjectpositioncontroller;

import com.seebattleserver.domain.cage.Cage;
import com.seebattleserver.domain.gameobject.GameObject;
import com.seebattleserver.domain.playingfield.PlayingField;
import com.seebattleserver.domain.rule.ClassicRule;
import com.seebattleserver.domain.rule.Rule;

import java.util.List;

import static com.seebattleserver.domain.cage.State.PROHIBITED_USE;
import static com.seebattleserver.domain.cage.State.USED;

public class ShipPositionController implements GameObjectPositionController {
    private List<String> shipCoordinates;
    private PlayingField playingField;
    private Rule rule = new ClassicRule();

    public ShipPositionController() {
    }

    @Override
    public void control() {
        for (String coordinatesCouple : shipCoordinates) {

        }
    }

    private boolean isAllObjectsOfCurrentSizeInstalled(int size) {
        List<GameObject> gameObjectsOfCurrentSize = playingField.getGameObjectsBySize(size);
        for(GameObject gameObject : gameObjectsOfCurrentSize) {
            if(!gameObject.wasInstalled()) {
                return false;
            }
        }
        return true;
    }

    private boolean isValidPlace(List<String> singleGameObjectCoordinates) {
        for (String coordinatesCouple : singleGameObjectCoordinates) {
            int x = Character.getNumericValue(coordinatesCouple.charAt(0));
            char y = coordinatesCouple.charAt(1);
            Cage cage = playingField.identifyCage(x, y);
            if ((cage.getState() == PROHIBITED_USE) || (cage.getState() == USED)) {
                return false;
            }
        }
        return true;
    }

    public boolean isValidHorizontalArrangement(List<String> singleGameObjectCoordinates) {
        if (singleGameObjectCoordinates.size() == 1) {
            return true;
        }
        if (isHorizontalArrangement(singleGameObjectCoordinates)) {
            for (int i = 0; i < singleGameObjectCoordinates.size() - 1; i++) {
                String coordinatesCouple = shipCoordinates.get(i);
                char y = coordinatesCouple.charAt(1);
                String nextCoordinatesCouple = shipCoordinates.get(i + 1);
                char yNext = nextCoordinatesCouple.charAt(1);
                if (rule.getNextCharCoordinate(y) != yNext) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    private boolean isValidVerticalArrangement(List<String> singleGameObjectCoordinates) {
        if (singleGameObjectCoordinates.size() == 1) {
            return true;
        }
        if (isValidVerticalArrangement(singleGameObjectCoordinates)) {
            for (int i = 0; i < singleGameObjectCoordinates.size() - 1; i++) {
                String coordinatesCouple = shipCoordinates.get(i);
                int x = Character.getNumericValue(coordinatesCouple.charAt(0));
                String nextCoordinatesCouple = shipCoordinates.get(i + 1);
                int xNext = nextCoordinatesCouple.charAt(0);
                if (x + 1 != xNext) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    private boolean isHorizontalArrangement(List<String> singleGameObjectCoordinates) {
        String firstCoordinatesCouple = singleGameObjectCoordinates.get(0);
        char x = firstCoordinatesCouple.charAt(0);
        for (String coordinateCouple : singleGameObjectCoordinates) {
            if (coordinateCouple.charAt(0) != x) {
                return false;
            }
        }
        return true;
    }

    private boolean VerticalArrangement(List<String> singleGameObjectCoordinates) {
        String firstCoordinatesCouple = singleGameObjectCoordinates.get(0);
        char y = firstCoordinatesCouple.charAt(1);
        for (String coordinateCouple : singleGameObjectCoordinates) {
            if (coordinateCouple.charAt(1) != y) {
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
