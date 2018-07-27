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

    @Test
    public void isValidGameObjectSize_whenGameObjectSizeIsNotValid_returnFalse() {
        int notValidGameObjectSize = 7;
        boolean result = rule.isValidGameObjectSize(notValidGameObjectSize);
        assertFalse(result);
    }

    @Test
    public void isValidGameObjectSize_whenGameObjectSizeIsValid_returnTrue() {
        int validGameObjectSize = 3;
        boolean result = rule.isValidGameObjectSize(validGameObjectSize);
        assertTrue(result);
    }

    @Test
    public void isValidCharCoordinate_whenCharCoordinateIsNotValid_returnFalse() {
        char notValidCharCoordinate = 'z';
        boolean result = rule.isValidCharCoordinate(notValidCharCoordinate);
        assertFalse(result);
    }

    @Test
    public void isValidCharCoordinate_whenCharCoordinateIsValid_returnTrue() {
        char validCharCoordinate = 'a';
        boolean result = rule.isValidCharCoordinate(validCharCoordinate);
        assertTrue(result);
    }

    @Test
    public void isValidIntCoordinate_whenIntCoordinateIsNotValid_returnFalse() {
        int notValidIntCoordinate = 30;
        boolean result = rule.isValidIntCoordinate(notValidIntCoordinate);
        assertFalse(result);
    }

    @Test
    public void isValidIntCoordinate_whenIntCoordinateIsValid_returnTrue() {
        int validIntCoordinate = 5;
        boolean result = rule.isValidIntCoordinate(validIntCoordinate);
        assertTrue(result);
    }

    @Test
    public void getNextCharCoordinate_whenCharCoordinateAreValidAndNotLastValid_returnNextCharCoordinate() {
        char charCoordinate = 'a';
        char nextCharCoordinate = 'b';
        char result = rule.getNextCharCoordinate(charCoordinate);
        assertEquals(nextCharCoordinate, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getNextCharCoordinate_whenCharCoordinateAreValidButLastValid_returnException() {
        char lastValidCharCoordinate = 'j';
        char result = rule.getNextCharCoordinate(lastValidCharCoordinate);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getNextCharCoordinate_whenCharCoordinateAreNotValid_returnException() {
        char notValidCharCoordinate = 'z';
        char result = rule.getNextCharCoordinate(notValidCharCoordinate);
    }

    @Test
    public void getPreviousCharCoordinate_whenCharCoordinateIsValidAndNotFirstValid_returnPreviousCHarCoordinate() {
        char charCoordinate = 'b';
        char previousCharCoordinate = 'a';
        char result = rule.getPreviousCharCoordinate(charCoordinate);
        assertEquals(previousCharCoordinate, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getPreviousCharCoordinate_whenCharCoordinateIsValidButFirstValid_returnException() {
        char firstValidCharCoordinate = 'a';
        char result = rule.getPreviousCharCoordinate(firstValidCharCoordinate);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getPreviousCharCoordinate_whenCharCoordinateIsNotValid_returnException() {
        char notValidCharCoordinate = 'z';
        char result = rule.getPreviousCharCoordinate(notValidCharCoordinate);
    }
}