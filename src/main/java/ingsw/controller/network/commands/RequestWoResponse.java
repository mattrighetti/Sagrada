package ingsw.controller.network.commands;

import java.io.Serializable;
import java.rmi.RemoteException;

public interface RequestWoResponse extends Serializable {
    void handle(RequestHandler requestHandler) throws RemoteException;
}
