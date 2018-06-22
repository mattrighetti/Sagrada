package ingsw.model.cards.patterncard;

import ingsw.model.Color;
import ingsw.model.Dice;

import java.io.Serializable;

public class Box implements Serializable {
    private Color color;
    private Integer value;
    private Dice dice;

    //COLORS
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

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

    private String setPrintColor(Color color) {
        switch (color) {
            case RED:
                return ANSI_RED_BACKGROUND;
            case BLUE:
                return ANSI_BLUE_BACKGROUND;
            case BLANK:
                return ANSI_WHITE_BACKGROUND;
            case GREEN:
                return ANSI_GREEN_BACKGROUND;
            case PURPLE:
                return ANSI_PURPLE_BACKGROUND;
            case YELLOW:
                return ANSI_YELLOW_BACKGROUND;
            default:
                return ANSI_RESET;
        }
    }

    @Override
    public String toString() {
        if (dice == null) {
            if (isValueSet()) return "[ " + String.valueOf(value) + " ->   ]";
            else return "[" + " " + setPrintColor(color) + String.valueOf(color.name().charAt(0)) + " " + ANSI_RESET + "]";
        } else {
            if (isValueSet()) return "[ " + String.valueOf(value) + " -> " + setPrintColor(dice.getDiceColor()) + " " + String.valueOf(dice.getDiceColor().name().charAt(0)) + " " + dice.getFaceUpValue() + " " + ANSI_RESET + "]";
            else return "[" + setPrintColor(color) + " " + String.valueOf(color.name().charAt(0)) + " -> " + ANSI_RESET + setPrintColor(dice.getDiceColor()) + String.valueOf(dice.getDiceColor().name().charAt(0)) + " " + dice.getFaceUpValue() + ANSI_RESET + setPrintColor(color) + " " + ANSI_RESET + "]" ;
        }
    }

    Boolean isDiceSet(){ return dice != null; }
}
