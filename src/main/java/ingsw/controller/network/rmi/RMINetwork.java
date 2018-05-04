package ingsw.controller.network.rmi;

import ingsw.controller.NetworkTransmitter;
import ingsw.controller.RemoteController;
import ingsw.model.RemoteSagradaGame;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Class that defines the rmi connection of the game
 */
public class RMINetwork implements NetworkTransmitter, Remote {
    private RemoteSagradaGame remoteSagradaGame;

    public RMINetwork() throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry();
        remoteSagradaGame = (RemoteSagradaGame) registry.lookup("sagrada");
    }
}
