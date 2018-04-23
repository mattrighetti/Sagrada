package ingsw.model.cards.publicoc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeepShadesTest {
    DeepShades deepShades;

    @BeforeEach
    void setUp() {
        deepShades = new DeepShades();
    }

    @Test
    void toStringTest() {
        assertEquals("DeepShades", deepShades.getName());
        assertEquals("PublicObjCard{'DeepShades'}", deepShades.toString());
    }

    @Test
    void checkTest() {
        //deepShades.check();
        //TODO test
    }
}