package ingsw.controller.network.commands;

import java.rmi.RemoteException;

public interface RequestHandler {

    Response handle(LoginUserRequest loginUserRequest);

    Response handle(ChosenPatternCardRequest chosenPatternCardRequest) throws RemoteException;
}
