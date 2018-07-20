package com.seebattleserver.application.controller.gameobjectarrangementcontroller.usergameobjectarrangementcontroller;

import com.seebattleserver.application.controller.Controller;
import com.seebattleserver.application.controller.gameobjectarrangementcontroller.gamestarthandler.ClassicGameStartHandler;
import com.seebattleserver.application.controller.gameobjectarrangementcontroller.gamestarthandler.GameStartHandler;
import com.seebattleserver.application.json.jsongameobjectcoordinates.jsongameobjectcoordinateshandler.JsonGameObjectCoordinatesHandler;
import com.seebattleserver.application.gameregistry.GameRegistry;
import com.seebattleserver.application.json.jsonmessage.JsonMessage;
import com.seebattleserver.application.user.User;
import com.seebattleserver.domain.gameobjectarrangement.GameObjectArrangement;
import com.seebattleserver.domain.gameobjectarrangement.UserGameObjectArrangement;
import com.seebattleserver.domain.playingfield.PlayingField;
import com.seebattleserver.service.sender.UserSender;
import org.springframework.web.socket.TextMessage;

import java.util.List;
import java.util.Map;

public class UserGameObjectArrangementController implements Controller {
    private User user;
    private UserSender userSender;
    private GameRegistry gameRegistry;

    public UserGameObjectArrangementController(User user, GameRegistry gameRegistry, UserSender userSender) {
        this.user = user;
        this.userSender = userSender;
        this.gameRegistry = gameRegistry;
    }

    @Override
    public void handle(TextMessage textMessage) {
        JsonGameObjectCoordinatesHandler jsonGameObjectCoordinatesHandler = new JsonGameObjectCoordinatesHandler();
        Map<Integer, List<List<String>>> coordinates = jsonGameObjectCoordinatesHandler.handle(textMessage);
        arrangeGameObjects(coordinates);
        notifyAboutSuccessfulGameObjectArrangement();
        startGameIfPossible();
    }

    private void arrangeGameObjects(Map<Integer, List<List<String>>> coordinates) {
        GameObjectArrangement userGameObjectArrangement = new UserGameObjectArrangement();
        PlayingField playingField = user.getPlayer().getPlayingField();
        userGameObjectArrangement.arrangeGameObjects(coordinates,playingField);
    }

    private void notifyAboutSuccessfulGameObjectArrangement() {
        userSender.sendMessage(user, new JsonMessage("Игровые объекты успешно установлены"));
    }

    private void startGameIfPossible() {
        GameStartHandler gameStartHandler = new ClassicGameStartHandler(user, gameRegistry, userSender);
        gameStartHandler.startGameIfPossible();
    }
}
