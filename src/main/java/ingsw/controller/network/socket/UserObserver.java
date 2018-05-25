package ingsw.controller.network.socket;

import ingsw.controller.network.Message;
import ingsw.controller.network.commands.CreateMatchResponse;
import ingsw.controller.network.commands.DiceMoveResponse;
import ingsw.controller.network.commands.DiceNotification;
import ingsw.controller.network.commands.PatternCardNotification;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface UserObserver extends Remote {

    void onJoin(int numberOfConnectedUsers) throws RemoteException;

    void sendMessage(Message message) throws RemoteException;

    void receiveDraftNotification() throws RemoteException;

    void sendResponse(DiceNotification diceNotification) throws RemoteException;

    void sendResponse(CreateMatchResponse createMatchResponse) throws RemoteException;

    void activateTurnNotification(List<Boolean[][]> booleanListGrid) throws RemoteException;

    void sendResponse(DiceMoveResponse diceMoveResponse) throws RemoteException;

    void sendPatternCards(PatternCardNotification patternCardNotification) throws RemoteException;

}
