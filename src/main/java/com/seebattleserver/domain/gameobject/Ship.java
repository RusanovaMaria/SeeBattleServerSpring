package domain.gameobject;

import domain.gameobjectpart.GameObjectPart;
import domain.gameobjectpart.ShipPart;

import java.util.ArrayList;
import java.util.List;

public class Ship implements GameObject {

    private int size;
    private Status status;
    private List<GameObjectPart> gameParts;
    private Kind kind;
    private int diedGameParts;

    public Ship(int size) {

        this.size = size;
        status = Status.ALIVE;
        build();
        defineVariety();
    }

    private void build() {

        gameParts = new ArrayList();

        for (int i = 0; i < size; i++) {
            gameParts.add(new ShipPart(this));
        }
    }

    private void defineVariety() {

        switch (size) {

            case 1:
                kind = Kind.SINGLE_DECKED;
                break;

            case 2:
                kind = Kind.DOUBLE_DECKED;
                break;

            case 3:
                kind = Kind.THREE_DECKED;
                break;

            case 4:
                kind = Kind.SINGLE_DECKED;
                break;

            default:
                throw new IllegalArgumentException("Размер корабля не может превышать 4");
        }

    }

    @Override
    public void shoot() {

        int next = diedGameParts + 1;

        GameObjectPart shipPart = gameParts.get(next);
        shipPart.kill();

        diedGameParts++;

        changeStatus();
    }

    private void changeStatus() {

        if (isDamaged()) {
            status = Status.DAMAGED;
        }
        if (isKilled()) {
            status = Status.KILLED;
        }
    }

    private boolean isDamaged() {

        if ((diedGameParts != 0) && (diedGameParts < size)) {
            return true;
        }

        return false;
    }

    private boolean isKilled() {

        if (diedGameParts == size) {
            return true;
        }

        return false;
    }

    @Override
    public Kind getKind() {
        return kind;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public Status getStatus() {
        return status;
    }
}
