package ingsw.controller.network.socket;

import ingsw.controller.network.Message;
import ingsw.controller.network.commands.DiceNotification;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface UserObserver extends Remote {

    void onJoin(int numberOfConnectedUsers) throws RemoteException;

    void sendMessage(Message message) throws RemoteException;

    void receiveDraftNotification() throws RemoteException;

    void sendResponse(DiceNotification diceNotification) throws RemoteException;

    void activateTurnNotification() throws RemoteException;
}
