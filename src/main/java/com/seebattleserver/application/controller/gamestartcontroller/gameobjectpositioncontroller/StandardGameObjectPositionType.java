package com.seebattleserver.application.controller.gamestartcontroller.gameobjectpositioncontroller;

import com.seebattleserver.application.gameobjectposition.GameObjectPosition;
import com.seebattleserver.application.gameobjectposition.StandardGameObjectPosition;
import com.seebattleserver.application.user.User;
import com.seebattleserver.domain.player.Player;
import com.seebattleserver.domain.playingfield.ClassicPlayingField;
import com.seebattleserver.domain.playingfield.PlayingField;
import com.seebattleserver.service.sender.UserSender;

public class StandardGameObjectPositionType implements GameObjectPositionType {
    private User user;

    public StandardGameObjectPositionType(User user) {
        this.user = user;
    }

    @Override
    public void apply() {
        PlayingField playingField = new ClassicPlayingField();
        GameObjectPosition gameObjectPosition = new StandardGameObjectPosition(playingField);
        gameObjectPosition.arrange();
        Player player = user.getPlayer();
        player.setPlayingField(playingField);
    }
}
