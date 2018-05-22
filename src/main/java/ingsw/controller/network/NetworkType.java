package ingsw.controller.network;

import java.rmi.RemoteException;

public interface NetworkType {

    boolean loginUser(String username) throws RemoteException;

    boolean createMatch(String matchName) throws RemoteException;

}
