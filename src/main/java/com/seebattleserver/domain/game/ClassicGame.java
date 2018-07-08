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

public class ClassicGame implements Game {
    private Player firstPlayer;
    private Player secondPlayer;
    private PlayingField firstPlayingField;
    private PlayingField secondPlayingField;

    public ClassicGame(Player firstPlayer, Player secondPlayer) {
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        initPlayingFields();
    }

    private void initPlayingFields() {
        firstPlayingField = firstPlayer.getPlayingField();
        secondPlayingField = secondPlayer.getPlayingField();
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
        PlayingField playingField = player.getPlayingField();
        System.out.println(playingField);
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
        if (firstPlayingField.isAllObjectsDied()) {
            return secondPlayer;
        } else if (secondPlayingField.isAllObjectsDied()) {
            return firstPlayer;
        } else {
            throw new IllegalStateException("Игра не окончена");
        }
    }
}

