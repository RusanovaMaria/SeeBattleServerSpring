package com.seebattleserver.domain.game;

import com.seebattleserver.domain.cage.Cage;
import com.seebattleserver.domain.cage.State;
import com.seebattleserver.domain.gameobject.GameObject;
import com.seebattleserver.domain.gameobject.Status;
import com.seebattleserver.domain.gameobjectpart.GameObjectPart;
import com.seebattleserver.domain.gameobjectposition.StandartGameObjectPosition;
import com.seebattleserver.domain.player.Player;
import com.seebattleserver.domain.playingfield.ClassicPlayingField;
import com.seebattleserver.domain.playingfield.PlayingField;

import java.util.HashMap;
import java.util.Map;

public class ClassicGame implements Game {
    private Player firstPlayer;
    private Player secondPlayer;
    private PlayingField firstPlayingField;
    private PlayingField secondPlayingField;
    private Map<Player, PlayingField> players;

    public ClassicGame(Player firstPlayer, Player secondPlayer) {
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        initPlayers();
    }

    private void initPlayers() {
        players = new HashMap();

        firstPlayingField = new ClassicPlayingField(new StandartGameObjectPosition());
        players.put(firstPlayer, firstPlayingField);

        secondPlayingField = new ClassicPlayingField(new StandartGameObjectPosition());
        players.put(secondPlayer, secondPlayingField);
    }

    @Override
    public boolean isEnd() {
        if ((firstPlayingField.isAllObjectsDied()) || (secondPlayingField.isAllObjectsDied())) {
            return true;
        }
        return false;
    }

    @Override
    public Result fire(Player player, int x, char y) {
        PlayingField playingField = players.get(player);
        Cage affectedCage = playingField.findCage(x, y);
        Result result = makeShot(affectedCage);
        return result;
    }

    private Result makeShot(Cage cage) {
        State state = cage.getState();
        switch (state) {
            case USED:
                return Result.REPEATED;
            case FREE:
                return Result.MISSED;
            case FULL:
                GameObject gameObject = ejectGameObject(cage);
                gameObject.shoot();
                return determineDamage(gameObject);
            default:
                cage.markAsUsed();
        }

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

    @Override
    public Player determineWinner() {
        if (firstPlayingField.isAllObjectsDied()) {
            return firstPlayer;
        } else if (secondPlayingField.isAllObjectsDied()) {
            return secondPlayer;
        } else {
            throw new IllegalArgumentException("Игра не окончена");
        }
    }
}

