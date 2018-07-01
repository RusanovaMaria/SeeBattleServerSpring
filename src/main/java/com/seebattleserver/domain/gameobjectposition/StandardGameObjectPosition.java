package com.seebattleserver.domain.gameobjectposition;

import com.seebattleserver.domain.cage.Cage;
import com.seebattleserver.domain.gameobject.GameObject;
import com.seebattleserver.domain.gameobjectaddition.GameObjectAddition;
import com.seebattleserver.domain.gameobjectaddition.StandardGameObjectAddition;
import com.seebattleserver.domain.gameobjectpart.GameObjectPart;
import com.seebattleserver.domain.playingfield.ClassicPlayingField;
import com.seebattleserver.domain.playingfield.PlayingField;

import java.util.ArrayList;
import java.util.List;

public class StandardGameObjectPosition implements GameObjectPosition {

    private PlayingField playingField;

    private List<GameObject> oneDeckShips;
    private List<GameObject> twoDeckShips;
    private List<GameObject> threeDeckShips;
    private List<GameObject> fourDeckShips;

    public StandardGameObjectPosition() {
        oneDeckShips = new ArrayList<>();
        twoDeckShips = new ArrayList<>();
        threeDeckShips = new ArrayList<>();
        fourDeckShips = new ArrayList<>();

        playingField = new ClassicPlayingField();
        addGameObjects();
        sortGameObjects();
    }


    @Override
    public void establish(PlayingField playingField) {
        positionOneDeckShips(playingField);
        positionTwoDeckShips(playingField);
        positionThreeDeckShips(playingField);
        positionFourDeckShips(playingField);
    }

    private void addGameObjects() {
        GameObjectAddition gameObjectAddition = new StandardGameObjectAddition(playingField);
        gameObjectAddition.add();
    }

    private void sortGameObjects() {
        List<GameObject> gameObjects = playingField.getGameObjects();
        for (int i = 0; i < gameObjects.size(); i++) {
            GameObject gameObject = gameObjects.get(i);
            sortObjectsBySize(gameObject);
        }
    }

    private void sortObjectsBySize(GameObject gameObject) {
        int size = gameObject.getSize();
        switch (size) {
            case 1:
                oneDeckShips.add(gameObject);
                break;
            case 2:
                oneDeckShips.add(gameObject);
                break;
            case 3:
                oneDeckShips.add(gameObject);
                break;
            case 4:
                oneDeckShips.add(gameObject);
                break;
            default:
                throw new IllegalArgumentException("Недопустимый размер игрового объекта");
        }
    }

    private void positionOneDeckShips(PlayingField playingField) {
        int[] x0 = {0};
        int[] x1 = {9};
        char y0 = 'a';
        char y1 = 'j';
        GameObject gameObject;

        gameObject = oneDeckShips.get(0);
        positionHorizontally(playingField, x0, y0, gameObject);

        gameObject = oneDeckShips.get(1);
        positionHorizontally(playingField, x0, y1, gameObject);

        gameObject = oneDeckShips.get(2);
        positionHorizontally(playingField, x1, y0, gameObject);

        gameObject = oneDeckShips.get(3);
        positionHorizontally(playingField, x1, y1, gameObject);
    }

    private void positionTwoDeckShips(PlayingField playingField) {
        int x0 = 0;
        int x1 = 2;
        int x2 = 9;
        char[] y = {'c', 'd'};
        GameObject gameObject;

        gameObject = twoDeckShips.get(0);
        positionVertically(playingField, x0, y, gameObject);

        gameObject = twoDeckShips.get(1);
        positionVertically(playingField, x1, y, gameObject);

        gameObject = twoDeckShips.get(2);
        positionVertically(playingField, x2, y, gameObject);
    }

    private void positionThreeDeckShips(PlayingField playingField) {
        int[] x = {4, 5, 6};
        char y0 = 'e';
        char y1 = 'g';
        GameObject gameObject;

        gameObject = threeDeckShips.get(0);
        positionHorizontally(playingField, x, y0, gameObject);

        gameObject = threeDeckShips.get(1);
        positionHorizontally(playingField, x, y1, gameObject);
    }

    private void positionFourDeckShips(PlayingField playingField) {
        int[] x = {2, 3, 4, 5};
        char y = 'a';

        GameObject gameObject = fourDeckShips.get(0);
        positionHorizontally(playingField, x, y, gameObject);
    }

    private void positionHorizontally(PlayingField playingField, int[] x, char y, GameObject gameObject) {
        List<GameObjectPart> objectParts = gameObject.getGameObjectParts();
        for (int i = 0; i < x.length; i++) {
            Cage cage = playingField.identifyCage(x[i], y);
            cage.setGameObjectPart(objectParts.get(i));
        }
    }

    private void positionVertically(PlayingField playingField, int x, char[] y, GameObject gameObject) {
        List<GameObjectPart> objectParts = gameObject.getGameObjectParts();
        for (int i = 0; i < y.length; i++) {
            Cage cage = playingField.identifyCage(x, y[i]);
            cage.setGameObjectPart(objectParts.get(i));
        }
    }
}
