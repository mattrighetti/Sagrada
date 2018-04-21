package ingsw.model.cards.publicoc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LightShadesTest {
    LightShades lightShades;

    @BeforeEach
    void setUp() {
        lightShades = new LightShades();
    }

    @Test
    void toStringTest() {
        assertEquals("LightShades", lightShades.getName());
        assertEquals("PublicObjCard{'LightShades'}", lightShades.toString());
    }

    @Test
    void checkTest() {
        lightShades.check();
        //TODO test
    }
}