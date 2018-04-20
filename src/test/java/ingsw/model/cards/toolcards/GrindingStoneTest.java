package ingsw.model.cards.toolcards;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GrindingStoneTest {
    GrindingStone grindingStone;

    @BeforeEach
    void setUp() {
        grindingStone = new GrindingStone();
    }

    @Test
    void toStringTest() {
        assertEquals("GrindingStone", grindingStone.getName());
        assertEquals("ToolCard{'GrindingStone'}", grindingStone.toString());
    }

    @Test
    void onActionTest() {
        grindingStone.action();
        //TODO test
    }
}