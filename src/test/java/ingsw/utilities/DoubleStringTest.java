package ingsw.utilities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DoubleStringTest {
    private DoubleString doubleString;
    private DoubleString doubleString1;

    @BeforeEach

    void setUp() {
        doubleString = new DoubleString("firstField", 10);
        doubleString1 = new DoubleString("firstField", 10);
    }

    @Test
    void getFirstField() {
        assertEquals("firstField", doubleString.getFirstField());
    }

    @Test
    void getSecondField() {
        assertEquals("10", doubleString.getSecondField());
    }

    @Test
    void equalsTest() {
        assertEquals(doubleString, doubleString1);
    }
}