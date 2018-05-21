package domain.game;

import domain.cage.Cage;
import domain.player.Player;
import domain.playingfield.ClassicPlayingField;

import java.util.Map;

public interface Game {

    boolean isEnd();

    Map<Player, ClassicPlayingField> getPlayers();

    Result fire(Player player, int x, char y);

}
