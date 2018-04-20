package ingsw.model.cards.patterncard;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KaleidoscopicDreamTest {
    KaleidoscopicDream kaleidoscopicDream;

    @BeforeEach
    void setUp(){
        kaleidoscopicDream = new KaleidoscopicDream();
    }

    @Test
    void toStringTest() {
        assertEquals("KaleidoscopicDream", kaleidoscopicDream.getName());
        assertEquals("PatternCard{'KaleidoscopicDream'}", kaleidoscopicDream.toString());
    }
}