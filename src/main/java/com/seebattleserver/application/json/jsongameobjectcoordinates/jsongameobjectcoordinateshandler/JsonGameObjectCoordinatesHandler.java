package com.seebattleserver.application.json.jsongameobjectcoordinates.jsongameobjectcoordinateshandler;

import com.google.gson.Gson;
import com.seebattleserver.application.json.jsongameobjectcoordinates.JsonGameObjectCoordinates;
import org.springframework.web.socket.TextMessage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonGameObjectCoordinatesHandler {
    private Gson gson;

    public JsonGameObjectCoordinatesHandler() {
        gson = new Gson();
    }

    public Map<Integer, List<List<String>>> handle(TextMessage textMessage) {
        Map<Integer, List<List<String>>> coordinates = new HashMap<>();
        JsonGameObjectCoordinates jsonGameObjectCoordinates = gson.fromJson(textMessage.getPayload(), JsonGameObjectCoordinates.class);
        coordinates.put(jsonGameObjectCoordinates.getGameObjectSize(), jsonGameObjectCoordinates.getCoordinates());
        return coordinates;
    }
}
