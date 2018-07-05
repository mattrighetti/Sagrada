package ingsw.model.cards.patterncard;

import ingsw.model.Color;
import ingsw.model.Dice;

import java.io.Serializable;

/**
 * Box is the atomic element of the grid. In a grid there are 20 Boxes.
 * They can have a value or a color and a dice set.
 */
public class Box implements Serializable {
    private Color color;
    private Integer value;
    private Dice dice;
    private Object obj;

    /**
     * Creates a new Box setting the color
     */
    public Box(Color color) {
        this.color = color;
    }

    /**
     * Creates a new Box setting the value
     */
    public Box(Integer value) {
        this.value = value;
    }

    /**
     * Returns Box color
     * @return Box color
     */
    public Color getColor() {
        return color;
    }

    /**
     * Returns Box value
     * @return Box value
     */
    public Integer getValue() {
        return value;
    }

    /**
     * Check if the value is set
     * @return true if value is set, otherwise false
     */
    public boolean isValueSet() {
        return value != null;
    }

    /**
     * Check if the dice is set
     * @return true if dice is set, otherwise false
     */
    Boolean isDiceSet() {
        return dice != null;
    }

    /**
     * Insert a dice inside a box
     * @param dice Dice to insert
     */
    public void insertDice(Dice dice) {
        this.dice = dice;
    }

    /**
     * Remove the dice in the box
     */
    public void removeDice() {
        if (dice != null) dice = null;
    }

    /**
     * Returns the dice in the box
     * @return Dice set in the box
     */
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

    /**
     * Check if two Box have the same color or value.
     * @param obj Dice to check
     * @return true if the dice are the same, otherwise false
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Box)) return false;
        else if (obj == this) return true;
        else if (((Box) obj).value != null && ((Box) obj).value.equals(this.value)) return true;
        return ((Box) obj).color.equals(this.color);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + value.hashCode();
        result = 31 * result + color.hashCode();
        return result;
    }
}
