package ingsw.model.cards.patterncard;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SunCatcherTest {
    SunCatcher sunCatcher;

    @BeforeEach
    void setUp(){
        sunCatcher = new SunCatcher();
    }

    @Test
    void toStringTest() {
        assertEquals("SunCatcher", sunCatcher.getName());
        assertEquals("PatternCard{'SunCatcher'}", sunCatcher.toString());
    }
}