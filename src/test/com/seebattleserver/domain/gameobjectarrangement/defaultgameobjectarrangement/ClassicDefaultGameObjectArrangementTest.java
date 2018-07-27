package com.seebattleserver.domain.gameobjectarrangement.defaultgameobjectarrangement;

import com.seebattleserver.domain.cage.Cage;
import com.seebattleserver.domain.cage.State;
import com.seebattleserver.domain.gameobject.GameObject;
import com.seebattleserver.domain.playingfield.ClassicPlayingField;
import com.seebattleserver.domain.playingfield.PlayingField;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class ClassicDefaultGameObjectArrangementTest {
    private static PlayingField playingField;

    @BeforeClass
    public static void setUp() {
       DefaultGameObjectArrangement defaultGameObjectArrangement = new ClassicDefaultGameObjectArrangement();
       playingField = new ClassicPlayingField();
       defaultGameObjectArrangement.arrangeGameObjectsByDefault(playingField);
    }

    @Test
    public void arrangeGameObjectsByDefault_whenCheckFullCageStatus_returnStatusFull() {
        Cage cage = playingField.identifyCage(5, 'h');
        assertEquals(State.FULL, cage.getState());
    }

    @Test
    public void arrangeGameObjectsByDefault_whenCheckProhibitedUseCageStatus_returnStatusProhibitedUse() {
        Cage cage = playingField.identifyCage(4, 'h');
        assertEquals(State.PROHIBITED_USE, cage.getState());
    }

    @Test
    public void arrangeGameObjectsByDefault_whenCheckFreeCageStatus_returnStatusFree() {
        Cage cage = playingField.identifyCage(3, 'h');
        assertEquals(State.FREE, cage.getState());
    }
}