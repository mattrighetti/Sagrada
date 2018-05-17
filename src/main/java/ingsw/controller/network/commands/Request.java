package ingsw.controller.network.commands;

import java.io.Serializable;
import java.rmi.RemoteException;

public interface Request extends Serializable {
    Response handle(RequestHandler requestHandler) throws RemoteException;
}
