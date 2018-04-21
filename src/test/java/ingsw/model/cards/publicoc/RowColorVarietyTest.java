package ingsw.model.cards.publicoc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RowColorVarietyTest {
    RowColorVariety rowColorVariety;

    @BeforeEach
    void setUp() {
        rowColorVariety = new RowColorVariety();
    }

    @Test
    void toStringTest() {
        assertEquals("RowColorVariety", rowColorVariety.getName());
        assertEquals("PublicObjCard{'RowColorVariety'}", rowColorVariety.toString());
    }

    @Test
    void checkTest() {
        rowColorVariety.check();
        //TODO test
    }
}