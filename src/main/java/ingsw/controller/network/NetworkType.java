package ingsw.controller.network;

import java.rmi.RemoteException;

public interface NetworkType {

    boolean loginUser(String username) throws RemoteException;

    void createMatch(String matchName) throws RemoteException;

    boolean joinExistingMatch(String matchName) throws RemoteException;
}
