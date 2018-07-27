package com.seebattleserver.application.controller.gameobjectarrangementcontroller.usergameobjectarrangementcontroller;

import com.seebattleserver.application.controller.Controller;
import com.seebattleserver.application.controller.gameobjectarrangementcontroller.gamestarthandler.ClassicGameStartHandler;
import com.seebattleserver.application.controller.gameobjectarrangementcontroller.gamestarthandler.GameStartHandler;
import com.seebattleserver.application.json.jsongameobjectcoordinates.jsongameobjectcoordinateshandler.JsonGameObjectCoordinatesHandler;
import com.seebattleserver.application.gameregistry.GameRegistry;
import com.seebattleserver.application.json.jsonmessage.JsonMessage;
import com.seebattleserver.application.user.User;
import com.seebattleserver.domain.coordinatecouple.CoordinatesCouple;
import com.seebattleserver.domain.gameobjectarrangement.GameObjectArrangement;
import com.seebattleserver.domain.gameobjectpositioncontroller.ShipInstallationController;
import com.seebattleserver.domain.playingfield.PlayingField;
import com.seebattleserver.domain.rule.ClassicRule;
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
        Map<Integer, List<List<CoordinatesCouple>>> coordinates = getCoordinates(textMessage);
        if (isNotNull(coordinates)) {
            PlayingField playingField = arrangeGameObjects(coordinates);
            selectActionAccordingToPlayingFieldState(playingField);
        } else {
            notifyAboutNotValidCoordinatesInput();
        }
    }

    private Map<Integer, List<List<CoordinatesCouple>>> getCoordinates(TextMessage textMessage) {
        Map<Integer, List<List<CoordinatesCouple>>> coordinates;
        try {
            JsonGameObjectCoordinatesHandler jsonGameObjectCoordinatesHandler = new JsonGameObjectCoordinatesHandler(new ClassicRule());
            coordinates = jsonGameObjectCoordinatesHandler.handle(textMessage);
        } catch (Exception ex) {
            coordinates = null;
        }
        return coordinates;
    }

    private PlayingField arrangeGameObjects(Map<Integer, List<List<CoordinatesCouple>>> coordinates) {
        GameObjectArrangement userGameObjectArrangement = new GameObjectArrangement(new ShipInstallationController());
        PlayingField playingField = user.getPlayer().getPlayingField();
        try {
            userGameObjectArrangement.arrangeGameObjects(coordinates, playingField);
            return playingField;
        } catch (Exception ex) {
            return null;
        }
    }

    private void selectActionAccordingToPlayingFieldState(PlayingField playingField) {
        if (isNull(playingField)) {
            notifyAboutGameObjectArrangementException();
        } else if (playingField.allGameObjectsInstalled()) {
            notifyAboutSuccessfulGameObjectArrangement();
            startGameIfPossible();
        } else {
            notifyAboutNextCoordinatesInput();
        }
    }

    private boolean isNotNull(Object o) {
        if (!isNull(o)) {
            return true;
        }
        return false;
    }

    private boolean isNull(Object o) {
        if (o == null) {
            return true;
        }
        return false;
    }

    private void notifyAboutGameObjectArrangementException() {
        userSender.sendMessage(user, new JsonMessage("Невозможно установить игровые объекты. Попробуйте еще раз"));
    }

    private void notifyAboutSuccessfulGameObjectArrangement() {
        userSender.sendMessage(user, new JsonMessage("Игровые объекты успешно установлены"));
    }

    private void startGameIfPossible() {
        GameStartHandler gameStartHandler = new ClassicGameStartHandler(user, gameRegistry, userSender);
        gameStartHandler.startGameIfPossible();
    }

    private void notifyAboutNextCoordinatesInput() {
        userSender.sendMessage(user, new JsonMessage("Введите следующий набор координат"));
    }

    private void notifyAboutNotValidCoordinatesInput() {
        userSender.sendMessage(user, new JsonMessage("Вы ввели не валидные координаты. Попробуйте еще раз"));
    }
}
