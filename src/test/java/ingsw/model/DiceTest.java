package ingsw.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DiceTest {

    private static Dice dice, diceNull;

    @BeforeEach
    private void setUp() {
        dice = new Dice(5, Color.BLUE);
    }

    @RepeatedTest(10)
    void roll() {
        dice.roll();
        int result = dice.getFaceUpValue();
        assertTrue(result > 0 && result < 7);
    }

    @Test
    void nullDiceException() {
        assertThrows(NullPointerException.class, () -> diceNull.setFaceUpValue(5));
    }

    @Test
    void getDiceColor() {
        assertNotEquals(Color.BLANK, dice.getDiceColor());
    }

    @Test
    void increasesByOneValue() {
        assertEquals(5, dice.getFaceUpValue());
        dice.increasesByOneValue();
        assertEquals(6, dice.getFaceUpValue());
    }

    @Test
    void decreasesByOneValue() {
        assertEquals(5, dice.getFaceUpValue());
        dice.decreasesByOneValue();
        assertEquals(4, dice.getFaceUpValue());
    }

    @Test
    void setOppositeFace() {
        assertEquals(5, dice.getFaceUpValue());
        dice.setOppositeFace();
        assertEquals(2, dice.getFaceUpValue());

        dice.setFaceUpValue(1);

        dice.setOppositeFace();
        assertEquals(6, dice.getFaceUpValue());

        dice.setFaceUpValue(2);

        dice.setOppositeFace();
        assertEquals(5, dice.getFaceUpValue());

        dice.setFaceUpValue(3);

        dice.setOppositeFace();
        assertEquals(4, dice.getFaceUpValue());

        dice.setFaceUpValue(4);

        dice.setOppositeFace();
        assertEquals(3, dice.getFaceUpValue());

        dice.setFaceUpValue(6);

        dice.setOppositeFace();
        assertEquals(1, dice.getFaceUpValue());
    }
}