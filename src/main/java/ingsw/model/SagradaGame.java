package ingsw.model;

import ingsw.controller.Controller;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

public class SagradaGame extends UnicastRemoteObject implements RemoteSagradaGame {
    private static SagradaGame sagradaGameSingleton;
    Map<String, Controller> matchesByName; // List of all open matches
    Map<String, User> connectedUsers; // List of connected users

    private SagradaGame() throws RemoteException {
        super();
        connectedUsers = new HashMap<>();
        matchesByName = new HashMap<>();
    }

    public static SagradaGame get() throws RemoteException {
        if (sagradaGameSingleton == null) {
            sagradaGameSingleton = new SagradaGame();
        }

        return sagradaGameSingleton;
    }

    @Override
    public synchronized User loginUser(String username) {
        User currentUser = new User(username);
        if (!connectedUsers.containsKey(username)) {
            if (connectedUsers.size() > 0)
                for (User user : connectedUsers.values())
                    user.updateUserConnected(connectedUsers.size() + 1);

            connectedUsers.put(username, currentUser);

            return connectedUsers.get(username);
        } else return null;
    }
}
