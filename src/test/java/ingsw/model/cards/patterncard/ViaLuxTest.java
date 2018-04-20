package ingsw.model.cards.patterncard;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ViaLuxTest {
    ViaLux viaLux;

    @BeforeEach
    void setUp(){
        viaLux = new ViaLux();
    }

    @Test
    void toStringTest() {
        assertEquals("ViaLux", viaLux.getName());
        assertEquals("PatternCard{'ViaLux'}", viaLux.toString());
    }
}