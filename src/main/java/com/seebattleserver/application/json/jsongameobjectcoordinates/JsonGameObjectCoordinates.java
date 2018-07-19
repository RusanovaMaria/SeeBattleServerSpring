package com.seebattleserver.application.json.jsongameobjectcoordinates;

import java.util.List;

public class JsonGameObjectCoordinates {
    private int gameObjectSize;
    private List<List<String>> coordinates;

    public JsonGameObjectCoordinates() {

    }

    public JsonGameObjectCoordinates(int gameObjectSize, List<List<String>> coordinates) {
        this.gameObjectSize = gameObjectSize;
        this.coordinates = coordinates;
    }

    public int getGameObjectSize() {
        return gameObjectSize;
    }

    public void setGameObjectSize(int gameObjectSize) {
        this.gameObjectSize = gameObjectSize;
    }

    public List<List<String>> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<List<String>> coordinates) {
        this.coordinates = coordinates;
    }
}
