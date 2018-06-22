package ingsw.exceptions;

public class InvalidDiceColorException extends Exception {
    public InvalidDiceColorException() {
        super("Trying to set dice color to blank");
    }
}
