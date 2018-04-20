package ingsw.model.cards.publicoc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ColorDiagonalsTest {
    ColorDiagonals colorDiagonals;

    @BeforeEach
    void setUp() {
        colorDiagonals = new ColorDiagonals();
    }

    @Test
    void toStringTest() {
        assertEquals("ColorDiagonals", colorDiagonals.getName());
        assertEquals("PublicObjCard{'ColorDiagonals'}", colorDiagonals.toString());
    }

    @Test
    void checkTest() {
        colorDiagonals.check();
        //TODO test
    }
}