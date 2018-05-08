package ingsw.model;

import ingsw.controller.network.socket.UserObserver;
import ingsw.exceptions.InvalidUsernameException;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteSagradaGame extends Remote {

    int getConnectedUsers() throws RemoteException;

    User loginUser(String username, UserObserver userObserver) throws RemoteException, InvalidUsernameException;

    void broadcastUsersConnected(String string) throws RemoteException;
}
