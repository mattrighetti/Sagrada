package ingsw.model.cards.patterncard;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BatlloTest {
    Batllo batllo;

    @BeforeEach
    void setUp() {
        batllo = new Batllo();
    }

    @Test
    void toStringTest() {
        assertEquals("Battlo", batllo.getName());
        assertEquals("PatternCard{'Battlo'}", batllo.toString());
    }
}