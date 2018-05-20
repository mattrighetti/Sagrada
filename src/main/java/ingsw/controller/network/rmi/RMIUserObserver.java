package ingsw.controller.network.rmi;

import ingsw.controller.network.Message;
import ingsw.controller.network.commands.DiceNotification;
import ingsw.controller.network.commands.IntegerResponse;
import ingsw.controller.network.commands.ResponseHandler;
import ingsw.controller.network.socket.UserObserver;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RMIUserObserver extends UnicastRemoteObject implements UserObserver {
    private ResponseHandler rmiController;

    public RMIUserObserver(RMIController rmiController) throws RemoteException {
        super();
        this.rmiController = rmiController;
    }

    @Override
    public void onJoin(int numberOfConnectedUsers) {
        new IntegerResponse(numberOfConnectedUsers).handle(rmiController);
    }

    @Override
    public void sendMessage(Message message) {

    }

    @Override
    public void receiveDraftNotification() {
        //TODO
    }

    @Override
    public void sendResponse(DiceNotification diceNotification) {
        //TODO
    }

    @Override
    public void activateTurnNotification() {
        //TODO
    }
}
