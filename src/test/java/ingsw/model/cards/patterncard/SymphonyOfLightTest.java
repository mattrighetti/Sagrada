package ingsw.model.cards.patterncard;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SymphonyOfLightTest {
    SymphonyOfLight symphonyOfLight;

    @BeforeEach
    void setUp(){
        symphonyOfLight = new SymphonyOfLight();
    }

    @Test
    void toStringTest() {
        assertEquals("SymphonyOfLight", symphonyOfLight.getName());
        assertEquals("PatternCard{'SymphonyOfLight'}", symphonyOfLight.toString());
    }
}