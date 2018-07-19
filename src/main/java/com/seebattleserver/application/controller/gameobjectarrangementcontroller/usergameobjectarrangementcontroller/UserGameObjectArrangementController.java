package com.seebattleserver.application.controller.gameobjectarrangementcontroller.usergameobjectarrangementcontroller;

import com.seebattleserver.application.controller.Controller;
import com.seebattleserver.application.controller.gameobjectarrangementcontroller.gamestarthandler.ClassicGameStartHandler;
import com.seebattleserver.application.controller.gameobjectarrangementcontroller.gamestarthandler.GameStartHandler;
import com.seebattleserver.application.gameobjectcoordinates.gameobjectcoordinateshandler.GameObjectCoordinatesHandler;
import com.seebattleserver.application.gameregistry.GameRegistry;
import com.seebattleserver.application.message.Message;
import com.seebattleserver.application.user.User;
import com.seebattleserver.domain.gameobjectarrangement.UserGameObjectArrangement;
import com.seebattleserver.domain.player.Player;
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
        GameObjectCoordinatesHandler gameObjectCoordinatesHandler = new GameObjectCoordinatesHandler();
        Map<Integer, List<List<String>>> coordinates = gameObjectCoordinatesHandler.handle(textMessage);
        UserGameObjectArrangement userGameObjectArrangement = new UserGameObjectArrangement(coordinates);
        PlayingField playingField = userGameObjectArrangement.arrange();
        Player player = user.getPlayer();
        player.setPlayingField(playingField);
        userSender.sendMessage(user, new Message("Игровые объекты успешно установлены"));
        GameStartHandler gameStartHandler = new ClassicGameStartHandler(user, gameRegistry, userSender);
        gameStartHandler.startGameIfPossible();
    }
}
