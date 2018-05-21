package domain.game;

import domain.gameobject.GameObject;
import domain.gameobject.Status;
import domain.gameobjectpart.GameObjectPart;
import domain.player.Player;
import domain.cage.Cage;
import domain.cage.State;
import domain.playingfield.ClassicPlayingField;
import domain.playingfield.PlayingField;
import domain.game.Result;

import java.util.HashMap;
import java.util.Map;

public class ClassicGame implements Game {

    private Player firstPlayer;
    private Player secondPlayer;
    private Map<Player, ClassicPlayingField> players;

    public ClassicGame(Player firstPlayer, Player secondPlayer) {

        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        initPlayers();
    }

    private void initPlayers() {

        players = new HashMap();

        players.put(firstPlayer, new ClassicPlayingField());
        players.put(secondPlayer, new ClassicPlayingField());
    }

    @Override
    public boolean isEnd() {
        return false;
    }

    @Override
    public Map<Player, ClassicPlayingField> getPlayers() {
        return players;
    }


    public Result fire(Player player, int x, char y) {

        PlayingField playingField = players.get(player);

        Cage affectedCage = playingField.findCage(x, y);

        return defineHit(affectedCage);
    }

    private Result defineHit(Cage cage) {

        State state = cage.determineState();

        switch (state) {

            case USED:
                return Result.REPEATED;

            case FREE:
                return Result.MISSED;

            case FULL:
                GameObject gameObject = ejectGameObject(cage);
                return determineDamage(gameObject);
        }

        cage.markAsUsed();

        throw new IllegalArgumentException("Неверное состояние компонента игрового поля");
    }

    private GameObject ejectGameObject(Cage cage) {

        GameObjectPart gameObjectPart = cage.getGameObjectPart();
        return gameObjectPart.getGameObject();
    }

    private Result determineDamage(GameObject gameObject) {

        Status status = gameObject.getStatus();

        switch (status) {

            case DAMAGED:
                return Result.GOT;

            case KILLED:
                return Result.KILLED;

            default:
                throw new IllegalArgumentException("Объект не был поврежден");
        }
    }
}

