package ingsw.model.cards.publicoc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ColorVarietyTest {
    ColorVariety colorVariety;

    @BeforeEach
    void setUp() {
        colorVariety = new ColorVariety();
    }

    @Test
    void toStringTest() {
        assertEquals("ColorVariety", colorVariety.getName());
        assertEquals("PublicObjCard{'ColorVariety'}", colorVariety.toString());
    }

    @Test
    void checkTest() {
        colorVariety.check();
        //TODO test
    }
}