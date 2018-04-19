package ingsw.model;

import ingsw.model.cards.exceptions.InvalidDiceColorException;
import ingsw.model.cards.exceptions.InvalidDiceValueException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class DiceTest {

    private static Dice dice, diceValExc, diceColExc, diceNull;

    @BeforeAll
    private static void setUp() throws InvalidDiceColorException {
        dice = new Dice(Color.BLUE);
        diceValExc = dice;
    }

    @RepeatedTest(10)
    void roll() throws InvalidDiceValueException {
        int result = dice.roll();
        assertTrue(result > 0 && result < 7);
    }

    @Test
    void invalidValueExceptionTest() {
        assertThrows(InvalidDiceValueException.class, () -> diceValExc.setFaceUpValue((new Random().nextInt(20)) + 7));
    }

    @Test
    void invalidColorException() {
        assertThrows(InvalidDiceColorException.class, () -> diceColExc = new Dice(Color.BLANK));
    }

    @Test
    void nullDiceException() {
        assertThrows(NullPointerException.class, () -> diceNull.setFaceUpValue(5));
    }

    @Test
    void getDiceColor() {
    }
}