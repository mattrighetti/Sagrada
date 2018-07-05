package ingsw.controller.network.socket;

import ingsw.controller.network.commands.*;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

public interface UserObserver extends Remote {

    /**
     * Activates the SagradaGame Pinger
     *
     * @throws RemoteException
     */
    void activatePinger() throws RemoteException;

    /**
     * Check if a User is active
     *
     * @throws RemoteException Thrown if the client has disconnected
     */
    void checkIfActive() throws RemoteException;

    /**
     * Notify a Player that he has lose the match
     * @param score Score
     *
     * @throws RemoteException
     */
    void notifyLost(int score) throws RemoteException;

    /**
     * Notify a Player that he has win the match
     * @param score Score
     *
     * @throws RemoteException
     */
    void notifyVictory(int score) throws RemoteException;

    /**
     * Send a Response from the server to a specific client.
     * @param response Response to send
     *
     * @throws RemoteException
     */
    void sendResponse(Response response) throws RemoteException;

    /**
     * After a login the number of connected users is updated
     * and broadcasted to all
     *
     * @param numberOfConnectedUsers
     * @throws RemoteException
     */
    void onJoin(int numberOfConnectedUsers) throws RemoteException;

    /**
     * Send a Notification from the server to a specific client.
     * @param notification Notification to send
     * @throws RemoteException
     */
    void receiveNotification(Notification notification) throws RemoteException;

    /**
     * Send a StartTurnNotification every time it's the player's first move
     * @param booleanMapGrid
     * @throws RemoteException
     */
    void activateTurnNotification(Map<String, Boolean[][]> booleanMapGrid) throws RemoteException;

}
