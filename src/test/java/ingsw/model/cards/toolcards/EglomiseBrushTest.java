package ingsw.model.cards.toolcards;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EglomiseBrushTest {
    EglomiseBrush eglomiseBrush;

    @BeforeEach
    void setUp() {
        eglomiseBrush = new EglomiseBrush();
    }

    @Test
    void toStringTest() {
        assertEquals("EglomiseBrush", eglomiseBrush.getName());
        assertEquals("ToolCard{'EglomiseBrush'}", eglomiseBrush.toString());
    }

    @Test
    void onActionTest() {
        //TODO test
    }
}