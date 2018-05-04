package ingsw.controller.network.socket;

import ingsw.controller.Controller;
import ingsw.model.SagradaGame;
import ingsw.model.User;

import java.rmi.RemoteException;

public class ServerController {
    private ClientHandler clientHandler;
    private final SagradaGame sagradaGame;
    private Controller controller;
    private User user;

    public ServerController(ClientHandler clientHandler) throws RemoteException {
        this.clientHandler = clientHandler;
        sagradaGame = SagradaGame.get();
        System.out.println("Got sagrada!");
    }


}
