package ingsw.view;

public interface GameUpdater {

    /**
     * Activates the drafted Die in the view
     */
    void activateDice();

    /**
     * Disables the die in the view
     */
    void disableDice();

    /**
     * Disables the roundtrack buttons in the view
     */
    void disableRoundTrack();

    /**
     * Set the place dice move flag true, after a place dice move
     */
    void setPlaceDiceMove();
}
