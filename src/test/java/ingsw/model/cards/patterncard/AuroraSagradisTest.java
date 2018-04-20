package ingsw.model.cards.patterncard;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuroraSagradisTest {
    AuroraSagradis auroraSagradis;

    @BeforeEach
    void setUp() {
        auroraSagradis = new AuroraSagradis();
    }

    @Test
    void toStringTest() {
        assertEquals("AuroraSagradis", auroraSagradis.getName());
        assertEquals("PatternCard{'AuroraSagradis'}", auroraSagradis.toString());
    }
}