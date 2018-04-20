package ingsw.model.cards.patterncard;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FirmitasTest {
    Firmitas firmitas;

    @BeforeEach
    void setUp(){
        firmitas = new Firmitas();
    }

    @Test
    void toStringTest() {
        assertEquals("Firmitas", firmitas.getName());
        assertEquals("PatternCard{'Firmitas'}", firmitas.toString());
    }
}