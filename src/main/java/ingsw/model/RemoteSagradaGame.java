package ingsw.model;

import ingsw.exceptions.InvalidUsernameException;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteSagradaGame extends Remote {

    User loginUser(String username) throws RemoteException, InvalidUsernameException;

    void broadcastUsersConnected(String string) throws RemoteException;
}
