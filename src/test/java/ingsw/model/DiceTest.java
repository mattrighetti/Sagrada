package ingsw.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DiceTest {

    private static Dice dice, diceNull;

    @BeforeAll
    private static void setUp() {
        dice = new Dice(Color.BLUE);
    }

    @RepeatedTest(10)
    void roll() {
        int result = dice.roll();
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
}