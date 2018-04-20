package ingsw.model.cards.toolcards;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LensCutterTest {
    LensCutter lensCutter;

    @BeforeEach
    void setUp() {
        lensCutter = new LensCutter();
    }

    @Test
    void toStringTest() {
        assertEquals("LensCutter", lensCutter.getName());
        assertEquals("ToolCard{'LensCutter'}", lensCutter.toString());
    }

    @Test
    void onActionTest() {
    lensCutter.action();
    //TODO test
    }
}