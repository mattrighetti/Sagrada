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
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                assertEquals(gravitasPatternCard.getGrid()[i][j].getColor(), windowFrame.getPatternCard().getGrid()[i][j].getColor());
                assertEquals(gravitasPatternCard.getGrid()[i][j].getValue(), windowFrame.getPatternCard().getGrid()[i][j].getValue());
            }
        }
    }
}