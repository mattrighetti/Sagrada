package ingsw.controller.network.rmi;

import ingsw.controller.network.commands.*;
import ingsw.controller.network.socket.UserObserver;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;

/**
 * Class that receiver the calls done from the server
 */
public class RMIUserObserver extends UnicastRemoteObject implements UserObserver {
    private transient ResponseHandler rmiController;

    /**
     * Set the RMIController
     * @param rmiController RmiController
     * @throws RemoteException
     */
    RMIUserObserver(RMIController rmiController) throws RemoteException {
        super();
        this.rmiController = rmiController;
    }

    /**
     * Calls the handle of the IntegerResponse on the RMIController
     * @param numberOfConnectedUsers
     */
    @Override
    public void onJoin(int numberOfConnectedUsers) {
        new IntegerResponse(numberOfConnectedUsers).handle(rmiController);
    }

    /**
     * Void method called from the server in order to check
     * if the user is active
     */
    @Override
    public void checkIfActive() {
        // This method is used by RMI to check if the connection is still active and the user too
        // If this throws a RemoteException the User must be disabled
    }

    /**
     * Activate the Pinger
     */
    @Override
    public void activatePinger() {
        //Void call
    }

    /**
     * Handle call to rmiController to handle a Response
     *
     * @param response Response to send
     */
    @Override
    public synchronized void sendResponse(Response response) {
        new Thread(() -> response.handle(rmiController)).start();
    }

    /**
     * Handle call to rmiController to handle an ActivateTurnNotification
     *
     * @param booleanMapGrid Available positions
     */
    @Override
    public void activateTurnNotification(Map<String, Boolean[][]> booleanMapGrid) {
        new StartTurnNotification(booleanMapGrid).handle(rmiController);
    }

    /**
     * Handle call to rmiController to handle a Notification
     *
     * @param notification Notification to send
     */
    @Override
    public void receiveNotification(Notification notification) {
        notification.handle(rmiController);
    }

    /**
     * Handle call to rmiController to handle a notifyVictory
     *
     * @param score Score
     */
    @Override
    public void notifyVictory(int score) {
        new VictoryNotification(score).handle(rmiController);
    }

    /**
     * Handle call to rmiController to handle a notifyLost
     *
     * @param score Score
     */
    @Override
    public void notifyLost(int score) {
        new LoseNotification(score).handle(rmiController);
    }
}
