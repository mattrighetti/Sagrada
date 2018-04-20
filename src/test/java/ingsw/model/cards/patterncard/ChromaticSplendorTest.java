package ingsw.model.cards.patterncard;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChromaticSplendorTest {
    ChromaticSplendor chromaticSplendor;

    @BeforeEach
    void setUp(){
        chromaticSplendor = new ChromaticSplendor();
    }

    @Test
    void toStringTest() {
        assertEquals("ChromaticSplendor", chromaticSplendor.getName());
        assertEquals("PatternCard{'ChromaticSplendor'}", chromaticSplendor.toString());
    }
}