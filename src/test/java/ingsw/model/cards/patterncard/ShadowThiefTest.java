package ingsw.model.cards.patterncard;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShadowThiefTest {
    ShadowThief shadowThief;

    @BeforeEach
    void setUp(){
        shadowThief = new ShadowThief();
    }

    @Test
    void toStringTest() {
        assertEquals("ShadowThief", shadowThief.getName());
        assertEquals("PatternCard{'ShadowThief'}", shadowThief.toString());
    }
}