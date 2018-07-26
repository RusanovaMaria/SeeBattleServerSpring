package com.seebattleserver.application.json.jsongameobjectcoordinates;

import com.seebattleserver.domain.coordinatecouple.CoordinatesCouple;

import java.util.List;

public class JsonGameObjectCoordinates {
    private int gameObjectSize;
    private List<List<CoordinatesCouple>> coordinates;

    public JsonGameObjectCoordinates() {

    }

    public JsonGameObjectCoordinates(int gameObjectSize, List<List<CoordinatesCouple>> coordinates) {
        this.gameObjectSize = gameObjectSize;
        this.coordinates = coordinates;
    }

    public int getGameObjectSize() {
        return gameObjectSize;
    }

    public void setGameObjectSize(int gameObjectSize) {
        this.gameObjectSize = gameObjectSize;
    }

    public List<List<CoordinatesCouple>> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<List<CoordinatesCouple>> coordinates) {
        this.coordinates = coordinates;
    }
}
