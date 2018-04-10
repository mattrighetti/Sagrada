package ingsw.cards.patterncard;

import ingsw.Color;
import ingsw.dice.Dice;

public class Box {
    private Color color;
    private int value;
    Dice dice;

    public Box(Color color) {
        this.color = color;
    }

    public Box(int value) {
        this.value = value;
    }

    public Box(Color color, int value) {
        this.color = color;
        this.value = value;
    }

    public Color getColor() {
        return color;
    }

    public int getValue() {
        return value;
    }

    public boolean isValueSet() {
        return value != -1;
    }

    public void insertDice(Dice dice) {
        this.dice = dice;
        //TODO the dice at this point must removed from the dice drafted --> dices (set).remove();
    }

    public void removeDice() {
        if (dice != null) dice = null;
        //TODO dice must be re-added?
    }
}
