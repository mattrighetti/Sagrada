package ingsw.model.cards.toolcards;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GrozingPliersTest {
    GrozingPliers grozingPliers;

    @BeforeEach
    void setUp() {
        grozingPliers = new GrozingPliers();
    }

    @Test
    void toStringTest() {
        assertEquals("GrozingPliers", grozingPliers.getName());
        assertEquals("ToolCard{'GrozingPliers'}", grozingPliers.toString());
    }

    @Test
    void onActionTest() {
        grozingPliers.action();
        //TODO test
    }
}