package com.seebattleserver.domain.game;

import com.seebattleserver.domain.gameobject.GameObject;
import com.seebattleserver.domain.gameobject.Status;
import com.seebattleserver.domain.gameobjectarrangement.defaultgameobjectarrangement.DefaultClassicGameObjectArrangement;
import com.seebattleserver.domain.gameobjectarrangement.defaultgameobjectarrangement.DefaultGameObjectArrangement;
import com.seebattleserver.domain.player.Player;
import com.seebattleserver.domain.playingfield.ClassicPlayingField;
import com.seebattleserver.domain.playingfield.PlayingField;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class ClassicGameTest {
    private Player firstPlayer;
    private Player secondPlayer;
    private Game game;

    @Before
    public void setUp() {
        initPlayers();
        PlayingField firstPlayingField = new ClassicPlayingField();
        PlayingField secondPlayingField = new ClassicPlayingField();
        DefaultGameObjectArrangement gameObjectArrangement = new DefaultClassicGameObjectArrangement();
        gameObjectArrangement.arrangeGameObjectsByDefault(firstPlayingField);
        gameObjectArrangement.arrangeGameObjectsByDefault(secondPlayingField);
        firstPlayer.setPlayingField(firstPlayingField);
        secondPlayer.setPlayingField(secondPlayingField);
        game = new ClassicGame(firstPlayer, secondPlayer);
    }

    private void initPlayers() {
        firstPlayer = new Player();
        secondPlayer = new Player();
    }

    @Test
    public void testIsEnd_whenGameIsNotEnd_returnFalse() {
        boolean result = game.isEnd();
        assertFalse(result);
    }

    @Test
    public void testIsEnd_whenGameIsEnd_returnTrue() {
        createGameWithPlayingFieldWithAllObjectsDiedForFirstPlayer();
        boolean result = game.isEnd();
        assertTrue(result);
    }
    @Test
    public void testFire_whenGetSlip_returnMissedResult() {
        Result result = game.fire(firstPlayer, 9, 'b');
        assertEquals(Result.MISSED, result);
    }
    @Test
    public void testFire_whenGetHit_returnKilledResult() {
        Result result = game.fire(firstPlayer, 0, 'a');
        assertEquals(Result.KILLED, result);
    }
    @Test
    public void testFire_whenGetHit_returnGotResult() {
        Result result = game.fire(firstPlayer, 5, 'h');
        assertEquals(Result.GOT, result);
    }
    @Test
    public void testFire_whenGetRepeat_returnRepeatedResult() {
        game.fire(firstPlayer, 5, 'a');
        Result result = game.fire(firstPlayer, 5, 'a');
        assertEquals(Result.REPEATED, result);
    }

    @Test (expected = IllegalStateException.class)
    public void testDetermineWinner_whenGameIsNotEnd_returnException() {
        Player player = game.determineWinner();
        assertEquals(secondPlayer, player);
    }

    @Test
    public void testDetermineWinner_whenGameIsEnd_returnWinner() {
        createGameWithPlayingFieldWithAllObjectsDiedForFirstPlayer();
        Player player = game.determineWinner();
        assertEquals(secondPlayer, player);
    }

    private void createGameWithPlayingFieldWithAllObjectsDiedForFirstPlayer() {
        PlayingField playingField = new ClassicPlayingField();
        killAllObjectsOfPlayingField(playingField);
        firstPlayer.setPlayingField(playingField);
        game = new ClassicGame(firstPlayer, secondPlayer);
    }

    private void killAllObjectsOfPlayingField(PlayingField playingField) {
        for (int i = 1; i <= 4; i++) {
            List<GameObject> gameObjectList = playingField.getGameObjectsBySize(i);
            for (int j = 0; j < gameObjectList.size(); j++) {
                GameObject gameObject = gameObjectList.get(j);
                gameObject.setStatus(Status.KILLED);
            }
        }
    }
}