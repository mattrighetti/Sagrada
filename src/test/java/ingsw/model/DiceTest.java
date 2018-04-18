package ingsw.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DiceTest {

    public static Dice dice;

    @BeforeAll
    public static void initializeDice() {
        dice = new Dice(Color.BLUE);
    }

    @Test
    @RepeatedTest(10)
    void roll() {
        int result = dice.roll();
        assertTrue(result > 0 && result < 7);
    }

    @Test
    void getFaceUpValue() {
        int result = dice.getFaceUpValue();
        assertTrue(result > 0 && result < 7, "FaceUpValue");
    }

    @Test
    void setFaceUpValue() {
        Dice dice = new Dice(Color.BLUE);
        dice.setFaceUpValue(3);

    }

    @Test
    void getDiceColor() {
    }
}