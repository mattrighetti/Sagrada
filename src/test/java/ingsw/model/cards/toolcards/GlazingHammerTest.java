package ingsw.model.cards.toolcards;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GlazingHammerTest {
    GlazingHammer glazingHammer;

    @BeforeEach
    void setUp() {
        glazingHammer = new GlazingHammer();
    }

    @Test
    void toStringTest() {
        assertEquals("GlazingHammer", glazingHammer.getName());
        assertEquals("ToolCard{'GlazingHammer'}", glazingHammer.toString());
    }

    @Test
    void onActionTest() {
        glazingHammer.action();
        //TODO test
    }
}