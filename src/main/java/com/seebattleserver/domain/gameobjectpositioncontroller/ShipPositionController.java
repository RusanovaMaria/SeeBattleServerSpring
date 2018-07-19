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

   /* private boolean isHorizontallyArrangement() {

    } */
}
