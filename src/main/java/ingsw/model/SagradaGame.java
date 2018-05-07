package ingsw.model;

import ingsw.controller.Controller;
import ingsw.exceptions.InvalidUsernameException;

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
    public synchronized User loginUser(String username) throws InvalidUsernameException {
        User currentUser = new User(username);
        if (!connectedUsers.containsKey(username)) {
            connectedUsers.put(username, currentUser);
            return connectedUsers.get(username);
        }
        throw new InvalidUsernameException("Username has been taken already");
    }

    @Override
    public void broadcastUsersConnected() {
        if (connectedUsers.size() > 0) {
            for (User user : connectedUsers.values())
                user.updateUserConnected(connectedUsers.size());
        }
    }
}
