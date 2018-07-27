package com.seebattleserver.domain.gameobjectaddition;

import com.seebattleserver.domain.playingfield.ClassicPlayingField;
import com.seebattleserver.domain.playingfield.PlayingField;
import com.seebattleserver.domain.rule.ClassicRule;
import com.seebattleserver.domain.rule.Rule;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

public class ClassicGameObjectAdditionTest {
    private static GameObjectAddition gameObjectAddition;
    private static PlayingField playingField;
    private static Rule rule;

    @BeforeClass
    public static void setUp() {
        gameObjectAddition = new ClassicGameObjectAddition();
        playingField = new ClassicPlayingField();
        gameObjectAddition.addGameObjects(playingField);
        rule = new ClassicRule();
    }

    @Test
    public void addGameObjects_whenCountGameObjectsQuantityBySize1_return4() {
        int quantityGameObjectBySize1 = playingField.getGameObjectsBySize(1).size();
        assertEquals(rule.countQuantityOfObjects(1), quantityGameObjectBySize1);
        assertEquals(4, quantityGameObjectBySize1);
    }

    @Test
    public void addGameObjects_whenCountGameObjectsQuantityBySize2_return3() {
        int quantityGameObjectBySize2 = playingField.getGameObjectsBySize(2).size();
        assertEquals(rule.countQuantityOfObjects(2), quantityGameObjectBySize2);
        assertEquals(3, quantityGameObjectBySize2);
    }

    @Test
    public void addGameObjects_whenCountGameObjectsQuantityBySize3_return2() {
        int quantityGameObjectBySize3 = playingField.getGameObjectsBySize(3).size();
        assertEquals(rule.countQuantityOfObjects(3), quantityGameObjectBySize3);
        assertEquals(2, quantityGameObjectBySize3);
    }

    @Test
    public void addGameObjects_whenCountGameObjectsQuantityBySize4_return1() {
        int quantityGameObjectBySize4 = playingField.getGameObjectsBySize(4).size();
        assertEquals(rule.countQuantityOfObjects(4), quantityGameObjectBySize4);
        assertEquals(1, quantityGameObjectBySize4);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addGameObjects_whenCountGameObjectsQuantityWrongSize_return4() {
        int quantityGameObjectBySize1 = playingField.getGameObjectsBySize(5).size();
        assertEquals(rule.countQuantityOfObjects(5), quantityGameObjectBySize1);
    }
}