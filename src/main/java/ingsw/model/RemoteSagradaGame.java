package ingsw.model;

import ingsw.controller.network.socket.UserObserver;
import ingsw.exceptions.InvalidUsernameException;
import ingsw.utilities.DoubleString;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface RemoteSagradaGame extends Remote {

    int getConnectedUsers() throws RemoteException;

    User loginUser(String username, UserObserver userObserver) throws RemoteException, InvalidUsernameException;

    void logoutUser(String username) throws RemoteException;

    void createMatch(String matchName) throws RemoteException;

    void loginPrexistentPlayer(String matchName, User user) throws RemoteException;

    void deactivateUser(User user) throws RemoteException;

    void broadcastUsersConnected(String string) throws RemoteException;

    List<DoubleString> doubleStringBuilder() throws RemoteException;

    void loginUserToController(String matchName, String username) throws RemoteException;
}
