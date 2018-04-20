package ingsw.model;

import java.util.Random;

public class Dice {
    private int faceUpValue;
    private final Color diceColor;

    Dice(Color diceColor) {
        this.diceColor = diceColor;
    }

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
