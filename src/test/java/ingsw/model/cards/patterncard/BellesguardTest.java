package ingsw.model.cards.patterncard;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BellesguardTest {
    Bellesguard bellesguard;

    @BeforeEach
    void setUp(){
        bellesguard = new Bellesguard();
    }

    @Test
    void toStringTest() {
        assertEquals("Bellesguard", bellesguard.getName());
        assertEquals("PatternCard{'Bellesguard'}", bellesguard.toString());
    }
}