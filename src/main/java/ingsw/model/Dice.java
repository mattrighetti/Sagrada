package ingsw.model;

import java.util.Random;

public class Dice {
    private int faceUpValue;
    private final Color diceColor;

    public Dice(Color diceColor) {
        this.diceColor = diceColor;
    }

    /**
     * Draft the dice
     * get a random number between 1 and 6 and set the faceUpValue
     * @return the value of the launch
     */
    int roll() {
        int value = (new Random()).nextInt(6) + 1;
        setFaceUpValue(value);
        return value;
    }

    public int getFaceUpValue() {
        return faceUpValue;
    }

    public void setFaceUpValue(int faceUpValue) {
        this.faceUpValue = faceUpValue;
    }

    public Color getDiceColor() {
        return diceColor;
    }
}
