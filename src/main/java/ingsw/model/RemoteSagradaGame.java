package ingsw.model;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteSagradaGame extends Remote {
    User loginUser(String username) throws RemoteException;
}
