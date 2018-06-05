package ingsw.model.cards.toolcards;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CorkBackedStraightEdgeTest {
    CorkBackedStraightEdge corkBackedStraightEdge;

    @BeforeEach
    void setUp() {
        corkBackedStraightEdge = new CorkBackedStraightEdge();
    }

    @Test
    void toStringTest() {
        assertEquals("CorkBackedStraightEdge", corkBackedStraightEdge.getName());
        assertEquals("ToolCard{'CorkBackedStraightEdge'}", corkBackedStraightEdge.toString());
    }

    @Test
    void onActionTest() {
        //TODO test
    }
}