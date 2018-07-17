package com.seebattleserver.application.controller.gamestartcontroller.gameobjectpositioncontroller;

import com.seebattleserver.domain.gameobjectarrangement.GameObjectArrangement;
import com.seebattleserver.domain.gameobjectarrangement.DefaultGameObjectArrangement;
import com.seebattleserver.application.user.User;
import com.seebattleserver.domain.player.Player;
import com.seebattleserver.domain.playingfield.ClassicPlayingField;
import com.seebattleserver.domain.playingfield.PlayingField;

public class StandardGameObjectPositionType implements GameObjectPositionType {
    private User user;

    public StandardGameObjectPositionType(User user) {
        this.user = user;
    }

    @Override
    public void apply() {
        PlayingField playingField = new ClassicPlayingField();
        GameObjectArrangement gameObjectArrangement = new DefaultGameObjectArrangement(playingField);
        gameObjectArrangement.arrange();
        Player player = user.getPlayer();
        player.setPlayingField(playingField);
    }
}
