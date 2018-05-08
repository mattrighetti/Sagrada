package ingsw.controller.network.rmi;

import ingsw.controller.network.commands.*;
import ingsw.model.RemoteSagradaGame;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class RMIHandler implements Remote, RequestHandler {
    private ResponseHandler rmiController;
    private RemoteSagradaGame sagradaGame;

    public RMIHandler(RMIController rmiController) {
        try {
            this.sagradaGame = (RemoteSagradaGame) LocateRegistry.getRegistry().lookup("sagrada");
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
        this.rmiController = rmiController;
    }

    @Override
    public Response handle(LoginUserRequest loginUserRequest) {
        return null;
    }

    @Override
    public Response handle(ChosenPatternCardRequest chosenPatternCardRequest) {
        return null;
    }
}
