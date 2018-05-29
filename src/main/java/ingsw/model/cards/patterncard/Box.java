package ingsw.model.cards.patterncard;

import ingsw.model.Color;
import ingsw.model.Dice;

import java.io.Serializable;

public class Box implements Serializable {
    private Color color;
    private Integer value;
    private Dice dice;

    public Box(Color color) {
        this.color = color;
    }

    public Box(Integer value) {
        this.value = value;
    }

    public Color getColor() {
        return color;
    }

    public Integer getValue() {
        return value;
    }

    public boolean isValueSet() {
        return value != null;
    }

    public void insertDice(Dice dice) {
        this.dice = dice;
        //TODO the dice at this point must removed from the dice drafted --> dices (set).remove();
    }

    public void removeDice() {
        if (dice != null) dice = null;
        //TODO dice must be re-added?
    }

    public Dice getDice() {
        return dice;
    }

    @Override
    public String toString() {
        if (isValueSet()) return String.valueOf(value);
        else return color.toString();
    }

    Boolean isDiceSet(){ return dice != null; }
}
