package ingsw.model.cards.patterncard;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LuxMundiTest {
    LuxMundi luxMundi;

    @BeforeEach
    void setUp(){
        luxMundi = new LuxMundi();
    }

    @Test
    void toStringTest() {
        assertEquals("LuxMundi", luxMundi.getName());
        assertEquals("PatternCard{'LuxMundi'}", luxMundi.toString());
    }
}