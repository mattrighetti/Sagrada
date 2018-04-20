package ingsw.model.cards.patterncard;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ComitasTest {
    Comitas comitas;

    @BeforeEach
    void setUp(){
        comitas = new Comitas();
    }

    @Test
    void toStringTest() {
        assertEquals("Comitas", comitas.getName());
        assertEquals("PatternCard{'Comitas'}", comitas.toString());
    }
}