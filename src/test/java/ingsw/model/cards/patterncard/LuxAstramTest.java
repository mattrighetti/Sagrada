package ingsw.model.cards.patterncard;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LuxAstramTest {
    LuxAstram luxAstram;

    @BeforeEach
    void setUp(){
        luxAstram = new LuxAstram();
    }

    @Test
    void toStringTest() {
        assertEquals("LuxAstram", luxAstram.getName());
        assertEquals("PatternCard{'LuxAstram'}", luxAstram.toString());
    }
}