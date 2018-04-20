package ingsw.model.cards.patterncard;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuroraeMagnificusTest {
    AuroraeMagnificus auroraeMagnificus;

    @BeforeEach
    void setUp() {
        auroraeMagnificus = new AuroraeMagnificus();
    }

    @Test
    void toStringTest() {
        assertEquals("AuroraeMagnificus", auroraeMagnificus.getName());
        assertEquals("PatternCard{'AuroraeMagnificus'}", auroraeMagnificus.toString());
    }
}