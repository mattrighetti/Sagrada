package ingsw.model.cards.publicoc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ColumnColorVarietyTest {
    ColumnColorVariety columnColorVariety;

    @BeforeEach
    void setUp() {
        columnColorVariety = new ColumnColorVariety();
    }

    @Test
    void toStringTest() {
        assertEquals("ColumnColorVariety", columnColorVariety.getName());
        assertEquals("PublicObjCard{'ColumnColorVariety'}", columnColorVariety.toString());
    }

    @Test
    void checkTest() {
        //columnColorVariety.check();
        //TODO test
    }

}