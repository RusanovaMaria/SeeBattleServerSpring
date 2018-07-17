package com.seebattleserver.application.gameobjectcoordinates.gameobjectcoordinateshandler;

import com.google.gson.Gson;
import com.seebattleserver.application.gameobjectcoordinates.GameObjectCoordinates;
import org.springframework.web.socket.TextMessage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameObjectCoordinatesHandler {
    private Gson gson;

    public GameObjectCoordinatesHandler() {
        gson = new Gson();
    }

    public Map<Integer, List<List<String>>> handle(TextMessage textMessage) {
        Map<Integer, List<List<String>>> coordinates = new HashMap<>();
        GameObjectCoordinates gameObjectCoordinates = gson.fromJson(textMessage.getPayload(), GameObjectCoordinates.class);
        coordinates.put(gameObjectCoordinates.getGameObjectSize(), gameObjectCoordinates.getCoordinates());
        return coordinates;
    }
}
