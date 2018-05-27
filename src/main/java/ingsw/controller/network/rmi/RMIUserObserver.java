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
    public void sendResponse(DraftedDiceResponse draftedDiceResponse) {
        draftedDiceResponse.handle(rmiController);
    }

    @Override
    public void sendResponse(CreateMatchResponse createMatchResponse) {
        createMatchResponse.handle(rmiController);
    }

    @Override
    public void sendResponse(DiceMoveResponse diceMoveResponse) {
        //TODO
    }

    @Override
    public void sendResponse(PatternCardNotification patternCardNotification) {
        patternCardNotification.handle(rmiController);
    }

    @Override
    public void sendResponse(BoardDataResponse boardDataResponse) {
        boardDataResponse.handle(rmiController);
    }

    @Override
    public void activateTurnNotification(List<Boolean[][]> booleanListGrid) {
        //TODO
    }

    @Override
    public void receiveNotification(Notification notification) {
        notification.handle(rmiController);
    }
}
