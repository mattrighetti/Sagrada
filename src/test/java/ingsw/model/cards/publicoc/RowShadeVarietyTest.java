package ingsw.model.cards.publicoc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RowShadeVarietyTest {
    RowShadeVariety rowShadeVariety;

    @BeforeEach
    void setUp() {
        rowShadeVariety = new RowShadeVariety();
    }

    @Test
    void toStringTest() {
        assertEquals("RowShadeVariety", rowShadeVariety.getName());
        assertEquals("PublicObjCard{'RowShadeVariety'}", rowShadeVariety.toString());
    }

    @Test
    void checkTest() {
        rowShadeVariety.check();
        //TODO test
    }
}