package ingsw.exceptions;

public class InvalidDiceValueException extends Exception {
    public InvalidDiceValueException() {
        super("Dice has not been rolled once");
    }
}
