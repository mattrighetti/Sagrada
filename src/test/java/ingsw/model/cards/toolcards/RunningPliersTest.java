package ingsw.model.cards.toolcards;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RunningPliersTest {
    RunningPliers runningPliers;

    @BeforeEach
    void setUp() {
        runningPliers = new RunningPliers();
    }

    @Test
    void toStringTest() {
        assertEquals("RunningPliers", runningPliers.getName());
        assertEquals("ToolCard{'RunningPliers'}", runningPliers.toString());
    }

    @Test
    void onActionTest() {
        runningPliers.action();
        //TODO test
    }
}