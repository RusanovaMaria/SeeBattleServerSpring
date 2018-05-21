package domain.playingfield;

import domain.cage.Cage;
import domain.gameobject.GameObject;
import domain.gameobject.Ship;
import domain.rule.ClassicRule;
import domain.rule.Rule;

import java.util.ArrayList;
import java.util.List;

public class ClassicPlayingField implements PlayingField {

    private final char[] CHAR_COORDINATE = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j'};
    private final int WIDTH = 10;
    private final int HEIGHT = 10;
    private List<GameObject> ships;
    private Cage[][] cages;


    public ClassicPlayingField() {

        generate();
        addGameObjects();
    }

    private void generate() {

        cages = new Cage[WIDTH][HEIGHT];

        for (int j = 0; j < HEIGHT; j++) {
        for (int i = 0; i < WIDTH; i++) {
                cages[i][j] = new Cage(i, CHAR_COORDINATE[j], null);
            }
        }
    }

    private void addGameObjects() {

        Rule rule = new ClassicRule();
        int[] shipsSize = rule.getGameObjectsSize();
        ships = new ArrayList();

        for (int i = 0; i < shipsSize.length; i++) {
            int size = shipsSize[i];
            GameObject ship = new Ship(size);
            int number = rule.countQuantityOfObjects(ship.getKind());
            for (int j = 0; j < number; j++) {
                ships.add(ship);
            }
        }
    }

    @Override
    public Cage findCage(int x, char y) {

        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                if ((cages[i][j].getX() == x) && (cages[i][j].getY() == y)) {
                    return cages[i][j];
                }
            }
        }
        throw new IllegalArgumentException("Клетка с данными координаами не найдена");
    }

    @Override
    public int getWidth() {
        return WIDTH;
    }

    @Override
    public int getHeight() {
        return HEIGHT;
    }

    @Override
    public char[] getCharCoordinate() {
        return CHAR_COORDINATE;
    }
}
