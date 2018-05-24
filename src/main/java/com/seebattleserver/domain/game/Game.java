package com.seebattleserver.domain.game;

import com.seebattleserver.domain.player.Player;
import com.seebattleserver.domain.playingfield.ClassicPlayingField;

import java.util.Map;

public interface Game {

    boolean isEnd();

    Map<Player, ClassicPlayingField> getPlayers();

    Result fire(Player player, int x, char y);

}
