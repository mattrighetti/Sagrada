package ingsw.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RoundTest {

    Round roundOne, roundTwo;

    @BeforeEach
    void setUp() {
        Set<Dice> dice = new HashSet<>();
        dice.add(new Dice(Color.BLUE));
        dice.add(new Dice(Color.GREEN));
        roundOne = new Round(dice);
        roundTwo = new Round(dice);
    }

    @Test
    void getCurrentRound() {
        assertEquals(3, roundOne.getCurrentRound());
    }

    @Test
    void getNoRound() {
        assertTrue(roundOne.getNoRound() > 0);
        assertTrue(roundOne.getNoRound() > 1);
    }

    @Test
    void getUnusedDice() {
        assertNotNull(roundOne.getUnusedDice());
        assertNotNull(roundTwo.getUnusedDice());
        Set<Dice> diceSet = roundOne.getUnusedDice();
        for (Dice dice: diceSet) {
            assertNotNull(dice);
            assertNotNull(dice.getDiceColor());
            assertEquals(0, dice.getFaceUpValue());
        }
    }
}