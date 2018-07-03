package ingsw.utilities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TripleStringTest {
    private TripleString tripleString;

    @BeforeEach

    void setUp() {
        tripleString = new TripleString("TestF", "TestS", "TestT");
    }

    @Test
    void getFirstField() {
        assertEquals("TestF", tripleString.getFirstField());
    }

    @Test
    void getSecondField() {
        assertEquals("TestS", tripleString.getSecondField());
    }

    @Test
    void getThirdField() {
        assertEquals("TestT", tripleString.getThirdField());
    }
}