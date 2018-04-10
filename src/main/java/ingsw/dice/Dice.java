package ingsw.dice;

public abstract class Dice {
    protected int faceUpValue;

    public int roll() {
        int value = (int) (Math.random() * 6) +1;
        setFaceUpValue(value);
        return value;
    }

    public int getFaceUpValue() {
        return faceUpValue;
    }

    public void setFaceUpValue(int faceUpValue) {
        this.faceUpValue = faceUpValue;
    }
}
