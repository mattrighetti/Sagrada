package ingsw.controller.network;

import java.rmi.RemoteException;

public interface NetworkType {

    boolean loginUser(String username);

    void createMatch(String matchName) throws RemoteException;

}
