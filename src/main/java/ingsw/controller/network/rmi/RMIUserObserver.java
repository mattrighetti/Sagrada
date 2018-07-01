package ingsw.controller.network.rmi;

import ingsw.controller.network.commands.*;
import ingsw.controller.network.socket.UserObserver;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;

public class RMIUserObserver extends UnicastRemoteObject implements UserObserver {
    private transient ResponseHandler rmiController;

    RMIUserObserver(RMIController rmiController) throws RemoteException {
        super();
        this.rmiController = rmiController;
    }

    @Override
    public void onJoin(int numberOfConnectedUsers) {
        new IntegerResponse(numberOfConnectedUsers).handle(rmiController);
    }

    @Override
    public void checkIfActive() {
        // This method is used by RMI to check if the connection is still active and the user too
        // If this throws a RemoteException the User must be disabled
    }

    @Override
    public void sendResponse(Response response) {
        new Thread(() -> response.handle(rmiController)).start();
    }

    @Override
    public void activateTurnNotification(Map<String, Boolean[][]> booleanMapGrid) {
        new StartTurnNotification(booleanMapGrid).handle(rmiController);
    }

    @Override
    public void receiveNotification(Notification notification) {
        notification.handle(rmiController);
    }

    @Override
    public void notifyVictory(int score) {
        new VictoryNotification(score).handle(rmiController);
    }

    @Override
    public void notifyLost(int score) {
        new LoseNotification(score).handle(rmiController);
    }
}
