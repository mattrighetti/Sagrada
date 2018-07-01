package ingsw.model;

import ingsw.controller.network.socket.UserObserver;
import ingsw.exceptions.InvalidUsernameException;
import ingsw.utilities.DoubleString;
import ingsw.utilities.TripleString;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public interface RemoteSagradaGame extends Remote {

    void setMaxTurnSeconds(int maxTurnSeconds) throws RemoteException;

    void setMaxJoinMatchSeconds(int maxJoinMatchSeconds) throws RemoteException;

    int getConnectedUsers() throws RemoteException;

    void sendSelectedMatchHistory(String username, String selectedMatchName) throws RemoteException;

    List<TripleString> createRankingsList() throws RemoteException;

    Map<String, TripleString> createUserStats(String username) throws RemoteException;

    void sendFinishedMatchesList(String username) throws RemoteException;

    User loginUser(String username, UserObserver userObserver) throws RemoteException, InvalidUsernameException;

    void logoutUser(String username) throws RemoteException;

    void createMatch(String matchName) throws RemoteException;

    void loginPrexistentPlayer(String matchName, String username) throws RemoteException;

    void deactivateUser(User user) throws RemoteException;

    void broadcastUsersConnected(String string) throws RemoteException;

    List<DoubleString> createAvailableMatchesList() throws RemoteException;

    void loginUserToController(String matchName, String username) throws RemoteException;

    void sendBundleData(String username) throws RemoteException;
}
