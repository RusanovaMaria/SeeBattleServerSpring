package com.seebattleserver.application.controller.usergameobjectarrangementcontroller;

import com.seebattleserver.application.controller.Controller;
import com.seebattleserver.application.gameobjectcoordinates.gameobjectcoordinateshandler.GameObjectCoordinatesHandler;
import org.springframework.web.socket.TextMessage;

import java.util.List;
import java.util.Map;

public class UserGameObjectArrangementController implements Controller {

    @Override
    public void handle(TextMessage textMessage) {
        GameObjectCoordinatesHandler gameObjectCoordinatesHandler = new GameObjectCoordinatesHandler();
        Map<Integer, List<List<String>>> coordinates = gameObjectCoordinatesHandler.handle(textMessage);
    }
}
