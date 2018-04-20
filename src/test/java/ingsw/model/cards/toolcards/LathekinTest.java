package ingsw.model.cards.toolcards;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LathekinTest {
    Lathekin lathekin;

    @BeforeEach
    void setUp() {
        lathekin = new Lathekin();
    }

    @Test
    void toStringTest() {
        assertEquals("Lathekin", lathekin.getName());
        assertEquals("ToolCard{'Lathekin'}", lathekin.toString());
    }

    @Test
    void onActionTest() {
        lathekin.action();
        //TODO test
    }
}