package ingsw.view;

import ingsw.controller.network.socket.SagradaSocketServer;
import ingsw.model.SagradaGame;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class LaunchServer {
    public static void main(String[] args) throws Exception {
        SagradaGame sagradaGame = SagradaGame.get();

        /* RMI registry connection */
        Registry registry = LocateRegistry.getRegistry();
        registry.rebind("sagrada", sagradaGame);
        System.out.println("Sagrada loaded on RMI registry");

        /* ServerSocket Connection */
        SagradaSocketServer sagradaSocketServer = new SagradaSocketServer(8000);

        try {
            sagradaSocketServer.run();
        } finally {
            sagradaSocketServer.close();
        }

    }
}
