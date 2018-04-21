package ingsw.model.cards.publicoc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MediumShadesTest {
    MediumShades mediumShades;

    @BeforeEach
    void setUp() {
        mediumShades = new MediumShades();
    }

    @Test
    void toStringTest() {
        assertEquals("MediumShades", mediumShades.getName());
        assertEquals("PublicObjCard{'MediumShades'}", mediumShades.toString());
    }

    @Test
    void checkTest() {
        mediumShades.check();
        //TODO test
    }


}