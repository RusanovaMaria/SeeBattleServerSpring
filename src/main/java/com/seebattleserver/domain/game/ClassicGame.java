package com.seebattleserver.domain.game;

import com.seebattleserver.domain.cage.Cage;
import com.seebattleserver.domain.cage.State;
import com.seebattleserver.domain.gameobject.GameObject;
import com.seebattleserver.domain.gameobject.Status;
import com.seebattleserver.domain.gameobjectkiller.GameObjectKiller;
import com.seebattleserver.domain.gameobjectkiller.ShipKiller;
import com.seebattleserver.domain.gameobjectpart.GameObjectPart;
import com.seebattleserver.domain.player.Player;
import com.seebattleserver.domain.playingfield.PlayingField;

import java.util.HashMap;
import java.util.Map;

public class ClassicGame implements Game {
    private Player firstPlayer;
    private Player secondPlayer;
    private PlayingField firstPlayingField;
    private PlayingField secondPlayingField;
    private Map<Player, PlayingField> playerPlayingFiels;

    public ClassicGame(Player firstPlayer, Player secondPlayer) {
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        firstPlayingField = firstPlayer.getPlayingField();
        secondPlayingField = secondPlayer.getPlayingField();
        initPlayingFields();
    }

    private void initPlayingFields() {
        playerPlayingFiels = new HashMap<>();
        playerPlayingFiels.put(secondPlayer, firstPlayingField);
        playerPlayingFiels.put(firstPlayer, secondPlayingField);
    }

    @Override
    public boolean isEnd() {
        if ((firstPlayingField.isAllGameObjectsDied()) || (secondPlayingField.isAllGameObjectsDied())) {
            return true;
        }
        return false;
    }

    @Override
    public Result fire(Player player, int x, char y) {
        PlayingField playingField = playerPlayingFiels.get(player);
        Cage affectedCage = playingField.identifyCage(x, y);
        shoot(affectedCage);
        Result result = getResult(affectedCage);
        affectedCage.markAsUsed();
        return result;
    }

    private void shoot(Cage cage) {
        State state = cage.getState();
        if (state == State.FULL) {
            GameObject gameObject = ejectGameObject(cage);
            GameObjectKiller gameObjectKiller = new ShipKiller(gameObject);
            gameObjectKiller.killPart();
        }
    }

    private GameObject ejectGameObject(Cage cage) {
        GameObjectPart gameObjectPart = cage.getGameObjectPart();
        return gameObjectPart.getGameObject();
    }

    private Result getResult(Cage cage) {
        State state = cage.getState();
        switch (state) {
            case USED:
                return Result.REPEATED;
            case FREE:
                return Result.MISSED;
            case FULL:
                GameObject gameObject = ejectGameObject(cage);
                return getResultOfDamage(gameObject);
            default:
                new IllegalArgumentException("Неверный статус для клетки игрового поля");
        }

        throw new IllegalArgumentException("Неверное состояние компонента игрового поля");
    }

    private Result getResultOfDamage(GameObject gameObject) {
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
        if (firstPlayingField.isAllGameObjectsDied()) {
            return secondPlayer;
        } else if (secondPlayingField.isAllGameObjectsDied()) {
            return firstPlayer;
        } else {
            throw new IllegalStateException("Игра не окончена");
        }
    }
}

