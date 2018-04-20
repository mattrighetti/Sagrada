package ingsw.model.cards.patterncard;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IndustriaTest {
    Industria industria;

    @BeforeEach
    void setUp(){
        industria = new Industria();
    }

    @Test
    void toStringTest() {
        assertEquals("Industria", industria.getName());
        assertEquals("PatternCard{'Industria'}", industria.toString());
    }
}