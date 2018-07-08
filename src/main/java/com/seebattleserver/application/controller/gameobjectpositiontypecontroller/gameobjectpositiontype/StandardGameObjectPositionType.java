package com.seebattleserver.application.controller.gameobjectpositiontypecontroller.gameobjectpositiontype;

import com.seebattleserver.application.gameobjectposition.GameObjectPosition;
import com.seebattleserver.application.gameobjectposition.StandardGameObjectPosition;
import com.seebattleserver.application.message.Message;
import com.seebattleserver.application.user.User;
import com.seebattleserver.domain.player.Player;
import com.seebattleserver.domain.playingfield.ClassicPlayingField;
import com.seebattleserver.domain.playingfield.PlayingField;
import com.seebattleserver.service.sender.UserSender;

public class StandardGameObjectPositionType implements GameObjectPositionType {
    private User user;
    private UserSender userSender;

    public StandardGameObjectPositionType(User user, UserSender userSender) {
        this.user = user;
        this.userSender = userSender;
    }


    @Override
    public void apply() {
        PlayingField playingField = new ClassicPlayingField();
        GameObjectPosition position = new StandardGameObjectPosition(playingField);
        position.arrange();
        Player player = user.getPlayer();
        player.setPlayingField(playingField);
        userSender.sendMessage(user, new Message("Игровые объекты установлены"));
        User userOpponent = user.getUserOpponent();
        if ()
    }
}
