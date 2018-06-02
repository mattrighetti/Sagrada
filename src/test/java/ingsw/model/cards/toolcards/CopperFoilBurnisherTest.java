package ingsw.model.cards.toolcards;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CopperFoilBurnisherTest {
    CopperFoilBurnisher copperFoilBurnisher;

    @BeforeEach
    void setUp() {
        copperFoilBurnisher = new CopperFoilBurnisher();
    }

    @Test
    void toStringTest() {
        assertEquals("CopperFoilBurnisher", copperFoilBurnisher.getName());
        assertEquals("ToolCard{'CopperFoilBurnisher'}", copperFoilBurnisher.toString());
    }


    @Test
    void onActionTest() {
        //TODO test
    }
}