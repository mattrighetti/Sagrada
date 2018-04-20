package ingsw.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ColorTest {
    Color color;

    @BeforeEach
    void setUp() {
        color = Color.PURPLE;
    }

    @Test
    void toStringTest() {
        assertEquals("Purple", color.toString());
    }
}