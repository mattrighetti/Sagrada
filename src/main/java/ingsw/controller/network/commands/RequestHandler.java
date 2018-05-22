package ingsw.controller.network.commands;

import java.rmi.RemoteException;

public interface RequestHandler {

    Response handle(LoginUserRequest loginUserRequest) throws RemoteException;

    Response handle(ChosenPatternCardRequest chosenPatternCardRequest) throws RemoteException;

    void handle(CreateMatchRequest createMatchRequest) throws RemoteException;

    Response handle(JoinMatchRequest joinMatchRequest);
}
