package ingsw.controller.network.rmi;

import ingsw.controller.network.Message;
import ingsw.controller.network.commands.*;
import ingsw.controller.network.socket.UserObserver;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

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
    public void sendMessage(Message message) {
        new MessageResponse(message).handle(rmiController);
    }

    @Override
    public void sendResponse(Response response) {
        response.handle(rmiController);
    }

    @Override
    public void activateTurnNotification(List<Boolean[][]> booleanListGrid) {
        new StartTurnNotification(booleanListGrid).handle(rmiController);
    }

    @Override
    public void receiveNotification(Notification notification) {
        notification.handle(rmiController);
    }
}
