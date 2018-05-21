package domain.gameobjectpart;

import domain.gameobject.GameObject;

public interface GameObjectPart {

    GameObject getGameObject();

    boolean isAlive();

    void kill();
}
