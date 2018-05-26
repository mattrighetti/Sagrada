package ingsw.controller.network.socket;

import ingsw.controller.network.Message;
import ingsw.controller.network.commands.*;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface UserObserver extends Remote {

    void onJoin(int numberOfConnectedUsers) throws RemoteException;

    void sendMessage(Message message) throws RemoteException;

    void receiveDraftNotification() throws RemoteException;

    void activateTurnNotification(List<Boolean[][]> booleanListGrid) throws RemoteException;

    /* OVERLOADING RESPONSES */

    void sendResponse(DiceNotification diceNotification) throws RemoteException;

    void sendResponse(CreateMatchResponse createMatchResponse) throws RemoteException;

    void sendResponse(DiceMoveResponse diceMoveResponse) throws RemoteException;

    void sendResponse(PatternCardNotification patternCardNotification) throws RemoteException;

    void sendResponse(BoardDataResponse boardDataResponse) throws RemoteException;
}
