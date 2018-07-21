package com.seebattleserver.application.controller.gameobjectarrangementcontroller.defaultgameobjectarrangementhandler;

import com.seebattleserver.application.user.User;
import com.seebattleserver.domain.gameobjectarrangement.ClassicGameObjectArrangement;
import com.seebattleserver.domain.gameobjectarrangement.GameObjectArrangement;
import com.seebattleserver.domain.playingfield.PlayingField;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefaultGameObjectArrangementHandler {
    private static final String[] ONE_DECK_SHIP_COORDINATES = {"0a", "9a", "0j", "9j"};
    private static final String[] TWO_DECK_SHIP_COORDINATES = {"2a3a", "2j3j", "0c0d"};
    private static final String[] THREE_DECK_SHIP_COORDINATES = {"5c5d5e", "7c7d7e"};
    private static final String[] FOUR_DECK_SHIP_COORDINATES = {"5j6j7j8j"};

    public void arrangeGameObjectsByDefault(PlayingField playingField) {
        Map<Integer, List<List<String>>> coordinates = getDefaultCoordinates();
        GameObjectArrangement gameObjectArrangement = new ClassicGameObjectArrangement();
        gameObjectArrangement.arrangeGameObjects(coordinates, playingField);
    }

    private Map<Integer, List<List<String>>> getDefaultCoordinates() {
        Map<Integer, List<List<String>>> coordinates = new HashMap();
        List<List<String>> oneDeckShipCoordinates = getShipCoordinatesBySize(ONE_DECK_SHIP_COORDINATES);
        coordinates.put(1, oneDeckShipCoordinates);
        List<List<String>> twoDeckShipCoordinates = getShipCoordinatesBySize(TWO_DECK_SHIP_COORDINATES);
        coordinates.put(2, twoDeckShipCoordinates);
        List<List<String>> threeDeckShipCoordinates = getShipCoordinatesBySize(THREE_DECK_SHIP_COORDINATES);
        coordinates.put(3, threeDeckShipCoordinates);
        List<List<String>> fourDeckShipCoordinates = getShipCoordinatesBySize(FOUR_DECK_SHIP_COORDINATES);
        coordinates.put(4, fourDeckShipCoordinates);
        return coordinates;
    }

    private List<List<String>> getShipCoordinatesBySize(String[] coordinatesByGameObjectSize) {
        List<List<String>> oneDeckShipCoordinates = new ArrayList();
        for(String coordinatesString : coordinatesByGameObjectSize) {
            List<String> coordinatesCouples = getCoordinatesCouples(coordinatesString);
            oneDeckShipCoordinates.add(coordinatesCouples);
        }
        return oneDeckShipCoordinates;
    }

    private List<String> getCoordinatesCouples(String coordinatesString) {
        List<String> coordinatesCouples = new ArrayList<>();
        int i = 0;
        while(i <= coordinatesString.length()-1) {
            String coordinateCouple = coordinatesString.substring(i, i+2);
            coordinatesCouples.add(coordinateCouple);
            i=+2;
        }
        return coordinatesCouples;
    }
}
