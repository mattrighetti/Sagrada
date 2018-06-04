package ingsw.model.cards.toolcards;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CorkBarckedStraightEdgeTest {
    CorkBarckedStraightEdge corkBarckedStraightEdge;

    @BeforeEach
    void setUp() {
        corkBarckedStraightEdge = new CorkBarckedStraightEdge();
    }

    @Test
    void toStringTest() {
        assertEquals("CorkBarckedStraightEdge", corkBarckedStraightEdge.getName());
        assertEquals("ToolCard{'CorkBarckedStraightEdge'}", corkBarckedStraightEdge.toString());
    }

    @Test
    void onActionTest() {
        //TODO test
    }
}