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
    }

    public void removeDice() {
        if (dice != null) dice = null;
    }

    public Dice getDice() {
        return dice;
    }

    @Override
    public String toString() {
        if (dice == null) {
            if (isValueSet()) return "[" + value + " ->  ]";
            else return "[" + color.name().charAt(0) + " ->  ]";
        } else {
            if (isValueSet())
                return "[" + value + " -> " + dice.getDiceColor().name().charAt(0) + dice.getFaceUpValue() + "]";
            else
                return "[" + color.name().charAt(0) + " -> " + dice.getDiceColor().name().charAt(0) + dice.getFaceUpValue() + "]";
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Box)) return false;
        else if (obj == this) return true;
        else if (((Box) obj).value != null && ((Box) obj).value.equals(this.value)) return true;
        return ((Box) obj).color.equals(this.color);
    }

    Boolean isDiceSet() {
        return dice != null;
    }
}
