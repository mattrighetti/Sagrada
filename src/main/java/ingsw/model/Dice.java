package ingsw.model;

import java.io.Serializable;
import java.util.Random;

public class Dice implements Serializable {
    private int faceUpValue;
    private final Color diceColor;

    public Dice(Color diceColor) {
        this.diceColor = diceColor;
    }

    public Dice(int faceUpValue, Color diceColor) {
        this.faceUpValue = faceUpValue;
        this.diceColor = diceColor;
    }

    /**
     * Draft the dice
     * get a random number between 1 and 6 and set the faceUpValue
     */
    void roll() {
        int value = (new Random()).nextInt(6) + 1;
        setFaceUpValue(value);
    }

    public int getFaceUpValue() {
        return faceUpValue;
    }

    public void setFaceUpValue(int faceUpValue) {
        this.faceUpValue = faceUpValue;
    }

    /**
     * Increases the face up value by one
     * Used by a tool card
     */
    public void increasesByOneValue(){
        if (faceUpValue < 6)
        faceUpValue++;
    }
    /**
     * Decreases the face up value by one
     * Used by a tool card
     */
    public void decreasesByOneValue(){
        if(faceUpValue > 1)
        faceUpValue--;
    }

    /**
     * Set the face up value opposite to the one the dice has before
     * Used for a tool card
     */
    public void setOppositeFace(){
        switch (faceUpValue){
            case 1:
                faceUpValue = 6;
                break;
            case 2:
                faceUpValue = 5;
                break;
            case 3:
                faceUpValue = 4;
                break;
            case 4:
                faceUpValue = 3;
                break;
            case 5:
                faceUpValue = 2;
                break;
            case 6:
                faceUpValue = 1;
        }
    }

    public Color getDiceColor() {
        return diceColor;
    }

    @Override
    public String toString() {
        if (faceUpValue != 0) {
            return diceColor.toString() + String.valueOf(faceUpValue);
        } else {
            return diceColor.toString();
        }
    }
}
