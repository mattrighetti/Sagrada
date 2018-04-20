package ingsw.model.cards.patterncard;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LuzCelestialTest {
    LuzCelestial luzCelestial;

    @BeforeEach
    void setUp(){
        luzCelestial = new LuzCelestial();
    }

    @Test
    void toStringTest() {
        assertEquals("LuzCelestial", luzCelestial.getName());
        assertEquals("PatternCard{'LuzCelestial'}", luzCelestial.toString());
    }

}