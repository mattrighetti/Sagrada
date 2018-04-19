package ingsw.model;

import ingsw.model.cards.exceptions.InvalidDiceColorException;
import ingsw.model.cards.exceptions.InvalidDiceValueException;

import java.util.Random;

public class Dice {
    private int faceUpValue;
    private final Color diceColor;

    Dice(Color diceColor) throws InvalidDiceColorException {
        if (diceColor.equals(Color.BLANK))
            throw new InvalidDiceColorException();
        else
            this.diceColor = diceColor;
    }

    int roll() throws InvalidDiceValueException {
        int value = (new Random()).nextInt(6) + 1;
        setFaceUpValue(value);
        return value;
    }

    public int getFaceUpValue() {
        return faceUpValue;
    }

    public void setFaceUpValue(int faceUpValue) throws InvalidDiceValueException {
        if (faceUpValue < 1 || faceUpValue > 6)
            throw new InvalidDiceValueException();
        else {
            this.faceUpValue = faceUpValue;
        }
    }

    public Color getDiceColor() {
        return diceColor;
    }
}
