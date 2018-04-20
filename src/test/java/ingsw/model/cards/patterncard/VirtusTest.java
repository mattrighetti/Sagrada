package ingsw.model.cards.patterncard;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VirtusTest {
    Virtus virtus;

    @BeforeEach
    void setUp(){
        virtus = new Virtus();
    }

    @Test
    void toStringTest() {
        assertEquals("Virtus", virtus.getName());
        assertEquals("PatternCard{'Virtus'}", virtus.toString());
    }
}