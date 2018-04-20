package ingsw.model.cards.patterncard;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SunsGloryTest {
    SunsGlory sunsGlory;

    @BeforeEach
    void setUp(){
        sunsGlory = new SunsGlory();
    }

    @Test
    void toStringTest() {
        assertEquals("SunsGlory", sunsGlory.getName());
        assertEquals("PatternCard{'SunsGlory'}", sunsGlory.toString());
    }
}