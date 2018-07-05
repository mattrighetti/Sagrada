package ingsw.model;

import ingsw.controller.network.socket.UserObserver;
import ingsw.exceptions.InvalidUsernameException;
import ingsw.utilities.DoubleString;
import ingsw.utilities.TripleString;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

/**
 * Remote Interface that RMI Clients use to send requests to SagradaGame.
 */
public interface RemoteSagradaGame extends Remote {

    /**
     * Method that set the maximum duration of a single turn.
     * @param maxTurnSeconds Time to execute the turn
     * @throws RemoteException
     */
    void setMaxTurnSeconds(int maxTurnSeconds) throws RemoteException;

    /**
     * Method that sets the maximum time to join a match.
     *
     * @param maxJoinMatchSeconds Time to join the match.
     * @throws RemoteException
     */
    void setMaxJoinMatchSeconds(int maxJoinMatchSeconds) throws RemoteException;

    /**
     * Returns the number of connected users.
     * @return Connected users
     * @throws RemoteException
     */
    int getConnectedUsers() throws RemoteException;

    /**
     * Sends request for a specified match history
     * @param username user who's asking for the history
     * @param selectedMatchName Match history
     * @throws RemoteException
     */
    void sendSelectedMatchHistory(String username, String selectedMatchName) throws RemoteException;

    /**
     * Sends request for creating the ranking of the connected users
     * @return Ranking
     * @throws RemoteException
     */
    List<TripleString> createRankingsList() throws RemoteException;

    /**
     * Send request for creating the user statistics
     * @param username user who's asking for the statistics
     * @return Statistics
     * @throws RemoteException
     */
    Map<String, TripleString> createUserStats(String username) throws RemoteException;

    /**
     * Sends the matches that are no more active.
     * @param username user who's asking for the list of the matches already terminated
     * @throws RemoteException
     */
    void sendFinishedMatchesList(String username) throws RemoteException;

    /**
     * Calls the method to login the user
     * @param username username of the user to log
     * @param userObserver User Observer of the user in order that the server is able to contact the user
     * @return User with the username and the userobserver inside
     * @throws RemoteException
     * @throws InvalidUsernameException
     */
    User loginUser(String username, UserObserver userObserver) throws RemoteException, InvalidUsernameException;

    void logoutUser(String username) throws RemoteException;

    /**
     * Request for creating a match
     * @param matchName Name of the match
     * @throws RemoteException
     */
    void createMatch(String matchName) throws RemoteException;

    /**
     * Calls the method to login a user in a match that has already starta and in which the player was participating.
     * @param matchName Name of the match
     * @param username Player username
     * @throws RemoteException
     */
    void loginPrexistentPlayer(String matchName, String username) throws RemoteException;

    /**
     * Broadcast the new User logged to the other host
     * @param string username
     * @throws RemoteException
     */
    void broadcastUsersConnected(String string) throws RemoteException;

    /**
     * Create a list of all available matches.
     * @return List of matches
     * @throws RemoteException
     */
    void writeUsersStatsToFile() throws RemoteException;

    List<DoubleString> createAvailableMatchesList() throws RemoteException;

    /**
     * Login the user to a new match that is not already started
     * @param matchName
     * @param username
     * @throws RemoteException
     */
    void loginUserToController(String matchName, String username) throws RemoteException;

    /**
     * Request all the data for the Lobby
     * @param username player username
     * @throws RemoteException
     */
    void sendBundleData(String username) throws RemoteException;

    /**
     * Sets the <code>user.active</code> to false. It means that the user has disconnected.
     * @param disconnectedUsername
     * @throws RemoteException
     */
    void deactivateUser(String disconnectedUsername) throws RemoteException;
}
