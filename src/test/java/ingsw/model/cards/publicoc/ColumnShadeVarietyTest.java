package ingsw.model.cards.publicoc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ColumnShadeVarietyTest {
    ColumnShadeVariety columnShadeVariety;

    @BeforeEach
    void setUp() {
        columnShadeVariety = new ColumnShadeVariety();
    }

    @Test
    void toStringTest() {
        assertEquals("ColumnShadeVariety", columnShadeVariety.getName());
        assertEquals("PublicObjCard{'ColumnShadeVariety'}", columnShadeVariety.toString());
    }

    @Test
    void checkTest() {
        //columnShadeVariety.check();
        //TODO test
    }
}