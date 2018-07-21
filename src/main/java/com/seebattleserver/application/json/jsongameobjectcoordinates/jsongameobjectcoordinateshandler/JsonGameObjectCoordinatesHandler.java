package com.seebattleserver.application.json.jsongameobjectcoordinates.jsongameobjectcoordinateshandler;

import com.google.gson.Gson;
import com.seebattleserver.application.json.jsongameobjectcoordinates.JsonGameObjectCoordinates;
import com.seebattleserver.domain.rule.Rule;
import org.springframework.web.socket.TextMessage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonGameObjectCoordinatesHandler {
    private Gson gson;
    private Rule rule;

    public JsonGameObjectCoordinatesHandler(Rule rule) {
        this.rule = rule;
        gson = new Gson();
    }

    public Map<Integer, List<List<String>>> handle(TextMessage textMessage) {
        Map<Integer, List<List<String>>> coordinates = new HashMap<>();
        JsonGameObjectCoordinates jsonGameObjectCoordinates = gson.fromJson(textMessage.getPayload(), JsonGameObjectCoordinates.class);
        coordinates.put(jsonGameObjectCoordinates.getGameObjectSize(), jsonGameObjectCoordinates.getCoordinates());
        if (isValidCoordinates(coordinates)) {
            return coordinates;
        }
        throw new IllegalArgumentException("Введены не валидные координаты");
    }

    private boolean isValidCoordinates(Map<Integer, List<List<String>>> coordinates) {
        if ((isValidGameObjectsSizes(coordinates)) && (isValidGameObjectsCoordinatesQuantity(coordinates))
                && (isValidCoordinatesForEachGameObject(coordinates))) {
            return true;
        }
        return false;
    }

    private boolean isValidGameObjectsSizes(Map<Integer, List<List<String>>> coordinates) {
        for (int gameObjectSize : coordinates.keySet()) {
            if (!rule.isValidGameObjectSize(gameObjectSize)) {
                return false;
            }
        }
        return true;
    }

    private boolean isValidGameObjectsCoordinatesQuantity(Map<Integer, List<List<String>>> coordinates) {
        for (int gameObjectSize : coordinates.keySet()) {
            if (rule.countQuantityOfObjects(gameObjectSize) == coordinates.get(gameObjectSize).size()) {
                return true;
            }
        }
        return false;
    }

    private boolean isValidCoordinatesForEachGameObject(Map<Integer, List<List<String>>> coordinates) {
        for (int gameObjectSize : coordinates.keySet()) {
            List<List<String>> gameObjectsCoordinatesByCurrentSize = coordinates.get(gameObjectSize);
            for (List<String> singleGameObjectCoordinates : gameObjectsCoordinatesByCurrentSize) {
                if (!isValidGameObjectCoordinates(singleGameObjectCoordinates, gameObjectSize)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isValidGameObjectCoordinates(List<String> singleGameObjectCoordinates, int gameObjectSize) {
        if (!isValidCoordinatesQuantityForSingleGameObject(singleGameObjectCoordinates, gameObjectSize)) {
            return false;
        }
        for (String coordinateCouple : singleGameObjectCoordinates) {
            int x;
            char y;
                x = getIntCoordinate(coordinateCouple);
                y = getCharCoordinate(coordinateCouple);
            if (!isValidCoordinatesCouple(x, y)) {
                return false;
            }
        }
        return true;
    }

    private boolean isValidCoordinatesQuantityForSingleGameObject(List<String> singleGameObjectCoordinates, int gameObjectSize) {
        if (singleGameObjectCoordinates.size() != gameObjectSize) {
            return true;
        }
        return false;
    }

    private int getIntCoordinate(String coordinateCouple) {
        int x;
        try {
            x = Character.getNumericValue(coordinateCouple.charAt(0));
        } catch (NumberFormatException ex) {
            x = -1;
        }
        return x;
    }

    private char getCharCoordinate(String coordinateCouple) {
        return coordinateCouple.charAt(1);
    }

    private boolean isValidCoordinatesCouple(int x, char y) {
        if ((rule.isValidIntCoordinateValue(x)) && (rule.isValidCharCoordinate(y))) {
            return true;
        }
        return false;
    }
}
