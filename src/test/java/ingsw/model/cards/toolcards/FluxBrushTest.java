package ingsw.model.cards.toolcards;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FluxBrushTest {
    FluxBrush fluxBrush;

    @BeforeEach
    void setUp() {
        fluxBrush = new FluxBrush();
    }

    @Test
    void toStringTest() {
        assertEquals("FluxBrush", fluxBrush.getName());
        assertEquals("ToolCard{'FluxBrush'}", fluxBrush.toString());
    }

    @Test
    void onActionTest() {
        //TODO test
    }
}