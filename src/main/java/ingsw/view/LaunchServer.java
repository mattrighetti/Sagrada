package ingsw.view;

import ingsw.controller.network.socket.SagradaSocketServer;
import ingsw.model.RemoteSagradaGame;
import ingsw.model.SagradaGame;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class LaunchServer {
    private static SagradaGame sagradaGame = SagradaGame.get();

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

        deployRMIServer(turnTimeOutSeconds, joinMatchSeconds, ip, port);

        /* ServerSocket Connection */
        SagradaSocketServer sagradaSocketServer = new SagradaSocketServer(8000);

        try {
            sagradaSocketServer.run();
        } finally {
            sagradaSocketServer.close();
        }

    }

    private static void deployRMIServer(int turnTimeOutSeconds, int joinMatchSeconds, String ip, int port) throws RemoteException, MalformedURLException {
        RemoteSagradaGame remoteSagradaGame = (RemoteSagradaGame) UnicastRemoteObject.exportObject(sagradaGame, 1100);
        Naming.rebind("rmi://" + ip + ":" + port + "/sagrada", remoteSagradaGame);

        remoteSagradaGame.setMaxTurnSeconds(turnTimeOutSeconds);
        remoteSagradaGame.setMaxJoinMatchSeconds(joinMatchSeconds);

        System.out.println("Sagrada loaded on RMI registry");
    }
}
