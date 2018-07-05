package ingsw.exceptions;

/**
 * Thrown when an Invalid Username is entered
 */
public class InvalidUsernameException extends Exception {
    public InvalidUsernameException(String message) {
        super(message);
    }
}
