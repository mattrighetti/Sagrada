package ingsw.model.cards.toolcards;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TapWheelTest {
    TapWheel tapWheel;

    @BeforeEach
    void setUp() {
        tapWheel = new TapWheel();
    }

    @Test
    void toStringTest() {
        assertEquals("TapWheel", tapWheel.getName());
        assertEquals("ToolCard{'TapWheel'}", tapWheel.toString());
    }

    @Test
    void onActionTest() {
        tapWheel.action();
        //TODO test
    }
}