package ingsw.model.cards.Exception;

public class InvalidDiceValueException extends Exception {
    public InvalidDiceValueException() {
        super("Dice has not been rolled once");
    }
}
