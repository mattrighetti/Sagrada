package ingsw.view;

import ingsw.controller.network.commands.BoardDataResponse;
import ingsw.controller.network.commands.PatternCardNotification;
import ingsw.utilities.DoubleString;

import java.util.List;

public interface GUIUpdater {
    /**
     * Set the Socket connection
     */
    void deploySocketClient();

    /**
     * Set the RMI connection
     */
    void deployRMIClient();

    /**
     * Launch the lobby View
     * @param username login username
     */
    void launchSecondGUI(String username);

    /**
     * Lauch the view in which the player choose the pattern card
     * @param patternCardNotification list of pattern cards to choose
     */
    void launchThirdGUI(PatternCardNotification patternCardNotification);

    /**
     * Launch the View of the game to start the match
     * @param boardDataResponse all the data needed in the match
     */
    void launchFourthGUI(BoardDataResponse boardDataResponse);

    /**
     * updates the list of connected users
     * @param connectedUsers
     */
    void updateConnectedUsers(int connectedUsers);

    /**
     * Updates the list of existing matches
     * @param matches
     */
    void updateExistingMatches(List<DoubleString> matches);

    /**
     * Switch to RMI connection
     */
    void changeToRMI();

    /**
     * Switch to Socket Connection
     */
    void changeToSocket();

    /**
     * Return the username
     * @return the username of the user
     */
    String getUsername();

    /**
     * Close tha application
     */
    void closeApplication();

    /**
     * set the username
     * @param username selected username
     */
    void setUsername(String username);

    /**
     * Launch the view to replay old matches
     */
    void launchHistoryView();
}
