package ingsw.view;

import ingsw.controller.network.socket.SagradaSocketServer;
import ingsw.model.SagradaGame;

import java.rmi.Naming;
import java.util.Scanner;

public class LaunchServer {
    public static void main(String[] args) throws Exception {
        String ip = args[0];
        Scanner scanner = new Scanner(System.in);
        int port = 1099;
        int joinMatchSeconds;
        int turnTimeOutSeconds;

        do {
            System.out.println("Insert max time to join a match (must be bigger than 5)");
            joinMatchSeconds = scanner.nextInt();
        } while (joinMatchSeconds < 5);

        do {
            System.out.println("Insert max time to make a turn");
            turnTimeOutSeconds = scanner.nextInt();
        } while (turnTimeOutSeconds < 30);

        scanner.close();

        SagradaGame sagradaGame = SagradaGame.get();

        sagradaGame.setMaxTurnSeconds(turnTimeOutSeconds);
        sagradaGame.setMaxJoinMatchSeconds(joinMatchSeconds);

        Naming.rebind("rmi://" + ip + ":" + port + "/sagrada", sagradaGame);

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
