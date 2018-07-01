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

    public StandardGameObjectPosition() {
        playingField = new ClassicPlayingField();
    }


    @Override
    public void establish() {
        positionOneDeckShips();
        positionTwoDeckShips();
        positionThreeDeckShips();
        positionFourDeckShips();
    }

    private void positionOneDeckShips() {
        List<GameObject> oneDeckShips = playingField.getGameObjects(1);

        int[] x0 = {0};
        int[] x1 = {9};
        char y0 = 'a';
        char y1 = 'j';
        GameObject gameObject;

        gameObject = oneDeckShips.get(0);
        positionHorizontally(x0, y0, gameObject);

        gameObject = oneDeckShips.get(1);
        positionHorizontally(x0, y1, gameObject);

        gameObject = oneDeckShips.get(2);
        positionHorizontally(x1, y0, gameObject);

        gameObject = oneDeckShips.get(3);
        positionHorizontally(x1, y1, gameObject);
    }

    private void positionTwoDeckShips() {
        List<GameObject> twoDeckShips = playingField.getGameObjects(2);

        int x0 = 0;
        int x1 = 2;
        int x2 = 9;
        char[] y = {'c', 'd'};
        GameObject gameObject;

        gameObject = twoDeckShips.get(0);
        positionVertically(x0, y, gameObject);

        gameObject = twoDeckShips.get(1);
        positionVertically(x1, y, gameObject);

        gameObject = twoDeckShips.get(2);
        positionVertically(x2, y, gameObject);
    }

    private void positionThreeDeckShips() {
        List<GameObject> threeDeckShips = playingField.getGameObjects(3);

        int[] x = {4, 5, 6};
        char y0 = 'e';
        char y1 = 'g';
        GameObject gameObject;

        gameObject = threeDeckShips.get(0);
        positionHorizontally(x, y0, gameObject);

        gameObject = threeDeckShips.get(1);
        positionHorizontally(x, y1, gameObject);
    }

    private void positionFourDeckShips() {
        List<GameObject> fourDeckShips = playingField.getGameObjects(4);

        int[] x = {2, 3, 4, 5};
        char y = 'a';

        GameObject gameObject = fourDeckShips.get(0);
        positionHorizontally(x, y, gameObject);
    }

    private void positionHorizontally(int[] x, char y, GameObject gameObject) {
        List<GameObjectPart> objectParts = gameObject.getGameObjectParts();
        for (int i = 0; i < x.length; i++) {
            Cage cage = playingField.identifyCage(x[i], y);
            cage.setGameObjectPart(objectParts.get(i));
        }
    }

    private void positionVertically(int x, char[] y, GameObject gameObject) {
        List<GameObjectPart> objectParts = gameObject.getGameObjectParts();
        for (int i = 0; i < y.length; i++) {
            Cage cage = playingField.identifyCage(x, y[i]);
            cage.setGameObjectPart(objectParts.get(i));
        }
    }
}
