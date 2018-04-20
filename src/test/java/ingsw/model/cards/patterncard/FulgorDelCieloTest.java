package ingsw.model.cards.patterncard;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FulgorDelCieloTest {
    FulgorDelCielo fulgorDelCielo;

    @BeforeEach
    void setUp(){
        fulgorDelCielo = new FulgorDelCielo();
    }

    @Test
    void toStringTest() {
        assertEquals("FulgorDelCielo", fulgorDelCielo.getName());
        assertEquals("PatternCard{'FulgorDelCielo'}", fulgorDelCielo.toString());
    }
}