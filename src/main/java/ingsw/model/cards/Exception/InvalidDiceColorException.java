package ingsw.model.cards.Exception;

public class InvalidDiceColorException extends Exception {
    public InvalidDiceColorException() {
        super("Trying to set dice color to blank");
    }
}
