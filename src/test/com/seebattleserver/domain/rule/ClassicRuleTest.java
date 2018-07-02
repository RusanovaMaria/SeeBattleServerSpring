package com.seebattleserver.domain.rule;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ClassicRuleTest {
    private Rule rule;

    @Before
    public void setUp() {
        rule = new ClassicRule();
    }

    @Test
    public void countQuantityOfObjects_whenSizeIs1_return4() {
        int result = rule.countQuantityOfObjects(1);
        assertEquals(result, 4);
    }

    @Test
    public void countQuantityOfObjects_whenSizeIs2_return3() {
        int result = rule.countQuantityOfObjects(2);
        assertEquals(result, 3);
    }

    @Test
    public void countQuantityOfObjects_whenSizeIs3_return2() {
        int result = rule.countQuantityOfObjects(3);
        assertEquals(result, 2);
    }

    @Test
    public void countQuantityOfObjects_whenSizeIs4_return1() {
        int result = rule.countQuantityOfObjects(4);
        assertEquals(result, 1);
    }

    @Test (expected = IllegalArgumentException.class)
    public void countQuantityOfObjects_whenSizeIsNotValid_returnException() {
        int result = rule.countQuantityOfObjects(5);
    }
}