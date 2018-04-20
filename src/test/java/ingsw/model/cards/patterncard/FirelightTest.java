package ingsw.model.cards.patterncard;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FirelightTest {
    Firelight firelight;

    @BeforeEach
    void setUp(){
        firelight = new Firelight();
    }

    @Test
    void toStringTest() {
        assertEquals("Firelight", firelight.getName());
        assertEquals("PatternCard{'Firelight'}", firelight.toString());
    }
}