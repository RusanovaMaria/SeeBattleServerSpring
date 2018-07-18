package com.seebattleserver.domain.gameobjectkiller;

import com.seebattleserver.domain.gameobject.GameObject;
import com.seebattleserver.domain.gameobject.Status;
import com.seebattleserver.domain.gameobjectpart.GameObjectPart;

import java.util.List;

public class ShipKiller implements GameObjectKiller {

    private GameObject ship;
    private int size;
    private int diedGameObjectParts;

    public ShipKiller(GameObject ship) {
        this.ship = ship;
        size = ship.getSize();
        diedGameObjectParts = ship.getDiedGameObjectParts();
    }

    @Override
    public void killPart() {
        if (!isKilled()) {
            killGameObjectPart();
            iterateDiedGameObjectPartsAndChangeStatus();
        }
    }

    private void killGameObjectPart() {
        List<GameObjectPart> gameObjectParts = ship.getGameObjectParts();
        GameObjectPart shipPart = gameObjectParts.get(diedGameObjectParts);
        shipPart.kill();
    }

    private void iterateDiedGameObjectPartsAndChangeStatus() {
        diedGameObjectParts++;
        ship.setDiedGameObjectParts(diedGameObjectParts);
        changeStatus();
    }

    private void changeStatus() {
        if (isDamaged()) {
            ship.setStatus(Status.DAMAGED);
        }
        if (isKilled()) {
            ship.setStatus(Status.KILLED);
        }
    }

    private boolean isDamaged() {
        if ((diedGameObjectParts != 0) && (diedGameObjectParts < size)) {
            return true;
        }
        return false;
    }

    private boolean isKilled() {
        if (diedGameObjectParts == size) {
            return true;
        }
        return false;
    }
}
