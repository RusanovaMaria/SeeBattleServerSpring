package com.seebattleserver.domain.gameobjectpositioncontroller;

import com.seebattleserver.domain.playingfield.PlayingField;

import java.util.List;

public class ShipPositionController implements GameObjectPositionController {
    private int shipSize;
    private List<String> shipCoordinates;
    private PlayingField playingField;

    public ShipPositionController(int shipSize, List<String> shipCoordinates, PlayingField playingField) {
        this.shipSize = shipSize;
        this.shipCoordinates = shipCoordinates;
        this.playingField = playingField;
    }

    @Override
    public void control() {
        for(String coordinatesCouple : shipCoordinates) {

        }
    }

    private boolean isHorizontalArrangement(List<String> singleGameObjectCoordinates, int gameObjectSize) {
        if(gameObjectSize == 1) {
            return true;
        }
        String firstCoordinatesCouple = singleGameObjectCoordinates.get(0);
        char x = firstCoordinatesCouple.charAt(0);
        for(String coordinateCouple : singleGameObjectCoordinates) {
            if(coordinateCouple.charAt(0) != x) {
                return false;
            }
        }
        return true;
    }

    private boolean VerticalArrangement(List<String> singleGameObjectCoordinates, int gameObjectSize) {
        if(gameObjectSize == 1) {
            return true;
        }
        String firstCoordinatesCouple = singleGameObjectCoordinates.get(0);
        char y = firstCoordinatesCouple.charAt(1);
        for(String coordinateCouple : singleGameObjectCoordinates) {
            if(coordinateCouple.charAt(1) != y) {
                return false;
            }
        }
        return true;
    }
}
