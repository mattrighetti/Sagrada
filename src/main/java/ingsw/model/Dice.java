package ingsw.model;

public class Dice {
    private int faceUpValue;
    private final Color diceColor;

    public Dice(Color diceColor) {
        this.diceColor = diceColor;
    }

    public int roll() {
        int value = (int) (Math.random() * 6) + 1;
        setFaceUpValue(value);
        return getFaceUpValue();
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
