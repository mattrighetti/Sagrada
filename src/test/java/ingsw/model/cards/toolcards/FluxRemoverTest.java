package ingsw.model.cards.toolcards;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FluxRemoverTest {
    FluxRemover fluxRemover;


    @BeforeEach
    void setUp() {
        fluxRemover = new FluxRemover();
    }

    @Test
    void toStringTest() {
        assertEquals("FluxRemover", fluxRemover.getName());
        assertEquals("ToolCard{'FluxRemover'}", fluxRemover.toString());
    }

    @Test
    void onActionTest() {
        fluxRemover.action();
        //TODO test
    }
}