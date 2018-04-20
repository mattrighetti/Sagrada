package ingsw.model;

import ingsw.model.cards.patterncard.Gravitas;
import ingsw.model.cards.patterncard.PatternCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WindowFrameTest {

    WindowFrame windowFrame;

    @BeforeEach
    void setUp() {
        windowFrame = new WindowFrame(new Gravitas());
    }

    @Test
    void getPatternCard() {
        Gravitas gravitasPatternCard = new Gravitas();
        assertEquals(gravitasPatternCard.getName(), windowFrame.getPatternCard().getName());
        assertEquals(gravitasPatternCard.getGrid(), windowFrame.getPatternCard().getGrid());
    }
}