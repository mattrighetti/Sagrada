package ingsw.model.cards.patterncard;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GravitasTest {
    Gravitas gravitas;

    @BeforeEach
    void setUp(){
        gravitas = new Gravitas();
    }

    @Test
    void toStringTest() {
        assertEquals("Gravitas", gravitas.getName());
        assertEquals("PatternCard{'Gravitas'}", gravitas.toString());
    }
}