package com.seebattleserver.domain.gameobjectarrangement;

import com.seebattleserver.domain.cage.Cage;
import com.seebattleserver.domain.gameobject.GameObject;
import com.seebattleserver.domain.gameobjectpart.GameObjectPart;
import com.seebattleserver.domain.playingfield.ClassicPlayingField;
import com.seebattleserver.domain.playingfield.PlayingField;

import java.util.List;

public class DefaultGameObjectArrangement implements GameObjectArrangement {
    private PlayingField playingField;

    @Override
    public PlayingField arrange() {
        playingField = new ClassicPlayingField();
        arrangeAllGameObjects();
        return playingField;
    }

    private void arrangeAllGameObjects() {
        arrangeOneDeckShips();
        arrangeTwoDeckShips();
        arrangeThreeDeckShips();
        arrangeFourDeckShips();
    }

    private void arrangeOneDeckShips() {
        List<GameObject> oneDeckShips = playingField.getGameObjectsBySize(1);

        int[] x0 = {0};
        int[] x1 = {9};
        char y0 = 'a';
        char y1 = 'j';
        GameObject gameObject;

        gameObject = oneDeckShips.get(0);
        arrangeGameObjectHorizontally(x0, y0, gameObject);

        gameObject = oneDeckShips.get(1);
        arrangeGameObjectHorizontally(x0, y1, gameObject);

        gameObject = oneDeckShips.get(2);
        arrangeGameObjectHorizontally(x1, y0, gameObject);

        gameObject = oneDeckShips.get(3);
        arrangeGameObjectHorizontally(x1, y1, gameObject);
    }

    private void arrangeTwoDeckShips() {
        List<GameObject> twoDeckShips = playingField.getGameObjectsBySize(2);

        int x0 = 0;
        int x1 = 2;
        int x2 = 9;
        char[] y = {'c', 'd'};
        GameObject gameObject;

        gameObject = twoDeckShips.get(0);
        arrangeGameObjectVertically(x0, y, gameObject);

        gameObject = twoDeckShips.get(1);
        arrangeGameObjectVertically(x1, y, gameObject);

        gameObject = twoDeckShips.get(2);
        arrangeGameObjectVertically(x2, y, gameObject);
    }

    private void arrangeThreeDeckShips() {
        List<GameObject> threeDeckShips = playingField.getGameObjectsBySize(3);

        int[] x = {4, 5, 6};
        char y0 = 'e';
        char y1 = 'g';
        GameObject gameObject;

        gameObject = threeDeckShips.get(0);
        arrangeGameObjectHorizontally(x, y0, gameObject);

        gameObject = threeDeckShips.get(1);
        arrangeGameObjectHorizontally(x, y1, gameObject);
    }

    private void arrangeFourDeckShips() {
        List<GameObject> fourDeckShips = playingField.getGameObjectsBySize(4);

        int[] x = {2, 3, 4, 5};
        char y = 'a';

        GameObject gameObject = fourDeckShips.get(0);
        arrangeGameObjectHorizontally(x, y, gameObject);
    }

    private void arrangeGameObjectHorizontally(int[] x, char y, GameObject gameObject) {
        List<GameObjectPart> objectParts = gameObject.getGameObjectParts();
        for (int i = 0; i < x.length; i++) {
            Cage cage = playingField.identifyCage(x[i], y);
            cage.setGameObjectPart(objectParts.get(i));
        }
        gameObject.install();
    }

    private void arrangeGameObjectVertically(int x, char[] y, GameObject gameObject) {
        List<GameObjectPart> objectParts = gameObject.getGameObjectParts();
        for (int i = 0; i < y.length; i++) {
            Cage cage = playingField.identifyCage(x, y[i]);
            cage.setGameObjectPart(objectParts.get(i));
        }
        gameObject.install();
    }
}
