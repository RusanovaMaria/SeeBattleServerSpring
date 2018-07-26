package com.seebattleserver.domain.gameobjectarrangement.defaultgameobjectarrangement;

import com.seebattleserver.domain.coordinatecouple.CoordinatesCouple;
import com.seebattleserver.domain.gameobjectarrangement.GameObjectArrangement;
import com.seebattleserver.domain.gameobjectpositioncontroller.ShipInstallationController;
import com.seebattleserver.domain.playingfield.PlayingField;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefaultClassicGameObjectArrangement implements DefaultGameObjectArrangement {
    private static final String[] ONE_DECK_SHIP_COORDINATES = {"0a", "9a", "0j", "9j"};
    private static final String[] TWO_DECK_SHIP_COORDINATES = {"2a3a", "2j3j", "0c0d"};
    private static final String[] THREE_DECK_SHIP_COORDINATES = {"5c5d5e", "7c7d7e"};
    private static final String[] FOUR_DECK_SHIP_COORDINATES = {"5j6j7j8j"};

    @Override
    public void arrangeGameObjectsByDefault(PlayingField playingField) {
        Map<Integer, List<List<CoordinatesCouple>>> coordinates = getDefaultCoordinates();
        GameObjectArrangement gameObjectArrangement = new GameObjectArrangement(new ShipInstallationController());
        gameObjectArrangement.arrangeGameObjects(coordinates, playingField);
    }

    private Map<Integer, List<List<CoordinatesCouple>>> getDefaultCoordinates() {
        Map<Integer, List<List<CoordinatesCouple>>> coordinates = new HashMap();
        List<List<CoordinatesCouple>> oneDeckShipCoordinates = getShipCoordinatesBySize(ONE_DECK_SHIP_COORDINATES);
        coordinates.put(1, oneDeckShipCoordinates);
        List<List<CoordinatesCouple>> twoDeckShipCoordinates = getShipCoordinatesBySize(TWO_DECK_SHIP_COORDINATES);
        coordinates.put(2, twoDeckShipCoordinates);
        List<List<CoordinatesCouple>> threeDeckShipCoordinates = getShipCoordinatesBySize(THREE_DECK_SHIP_COORDINATES);
        coordinates.put(3, threeDeckShipCoordinates);
        List<List<CoordinatesCouple>> fourDeckShipCoordinates = getShipCoordinatesBySize(FOUR_DECK_SHIP_COORDINATES);
        coordinates.put(4, fourDeckShipCoordinates);
        return coordinates;
    }

    private List<List<CoordinatesCouple>> getShipCoordinatesBySize(String[] coordinatesByGameObjectSize) {
        List<List<CoordinatesCouple>> gameObjectsOfCurrentSizeCoordinates = new ArrayList();
        for (String coordinatesString : coordinatesByGameObjectSize) {
            List<String> stringCoordinatesCouples = getCoordinatesCouplesInString(coordinatesString);
            List<CoordinatesCouple> coordinatesCouples = convertToCoordinatesCouples(stringCoordinatesCouples);
            gameObjectsOfCurrentSizeCoordinates.add(coordinatesCouples);
        }
        return gameObjectsOfCurrentSizeCoordinates;
    }

    private List<String> getCoordinatesCouplesInString(String coordinatesString) {
        List<String> coordinatesCouples = new ArrayList<>();
        int i = 0;
        while (i <= coordinatesString.length() - 1) {
            String coordinateCouple = coordinatesString.substring(i, i + 2);
            coordinatesCouples.add(coordinateCouple);
            i = i + 2;
        }
        return coordinatesCouples;
    }

    private List<CoordinatesCouple> convertToCoordinatesCouples(List<String> stringCoordinatesCouples) {
        List<CoordinatesCouple> coordinatesCouples = new ArrayList<>();
        for (String stringCoordinatesCouple : stringCoordinatesCouples) {
            int x = Character.getNumericValue(stringCoordinatesCouple.charAt(0));
            char y = stringCoordinatesCouple.charAt(1);
            coordinatesCouples.add(new CoordinatesCouple(x, y));
        }
        return coordinatesCouples;
    }
}
