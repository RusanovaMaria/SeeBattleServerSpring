package com.seebattleserver.application.controller.gamestartcontroller.gameobjectpositioncontroller;

import com.seebattleserver.application.gameobjectcoordinates.gameobjectcoordinateshandler.GameObjectCoordinatesHandler;

public class UserGameObjectPositionType implements GameObjectPositionType {
    private GameObjectCoordinatesHandler gameObjectCoordinatesHandler;

    public UserGameObjectPositionType() {
        gameObjectCoordinatesHandler = new GameObjectCoordinatesHandler();
    }

    @Override
    public void apply() {

    }
}
