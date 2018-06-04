package ingsw.view;

import ingsw.controller.network.socket.SagradaSocketServer;
import ingsw.model.SagradaGame;

import java.rmi.Naming;

public class LaunchServer {
    public static void main(String[] args) throws Exception {
        String ip = args[0];
        int port = 1099;

        SagradaGame sagradaGame = SagradaGame.get();

        Naming.rebind("rmi://"+ ip + ":" + port + "/sagrada", sagradaGame);

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
