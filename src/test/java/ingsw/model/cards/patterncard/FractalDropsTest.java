package ingsw.model.cards.patterncard;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FractalDropsTest {
    FractalDrops fractalDrops;

    @BeforeEach
    void setUp(){
        fractalDrops = new FractalDrops();
    }

    @Test
    void toStringTest() {
        assertEquals("FractalDrops", fractalDrops.getName());
        assertEquals("PatternCard{'FractalDrops'}", fractalDrops.toString());
    }
}