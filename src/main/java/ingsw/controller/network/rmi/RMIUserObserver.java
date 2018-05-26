package ingsw.controller.network.rmi;

import ingsw.controller.network.Message;
import ingsw.controller.network.commands.*;
import ingsw.controller.network.socket.UserObserver;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

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
        new MessageResponse(message).handle(rmiController);
    }

    @Override
    public void sendResponse(DraftedDiceResponse draftedDiceResponse) throws RemoteException {
        rmiController.handle(draftedDiceResponse);
    }

    @Override
    public void sendResponse(CreateMatchResponse createMatchResponse) throws RemoteException {
        rmiController.handle(createMatchResponse);
    }

    @Override
    public void sendResponse(DiceMoveResponse diceMoveResponse) throws RemoteException {
        //TODO
    }

    @Override
    public void sendResponse(PatternCardNotification patternCardNotification) {
        rmiController.handle(patternCardNotification);
    }

    @Override
    public void sendResponse(BoardDataResponse boardDataResponse) throws RemoteException {
        rmiController.handle(boardDataResponse);
    }

    @Override
    public void activateTurnNotification(List<Boolean[][]> booleanListGrid) throws RemoteException {
        //TODO
    }

    @Override
    public void receiveNotification(Notification notification) throws RemoteException {
        rmiController.handle(notification);
    }
}
