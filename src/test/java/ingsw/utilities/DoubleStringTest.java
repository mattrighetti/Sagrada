package ingsw.utilities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DoubleStringTest {
    private DoubleString doubleString;

    @BeforeEach

    void setUp() {
        doubleString = new DoubleString("firstField", 10);
    }

    @Test
    void getFirstField() {
        assertEquals("firstField", doubleString.getFirstField());
    }

    @Test
    void getSecondField() {
        assertEquals("10", doubleString.getSecondField());
    }
}