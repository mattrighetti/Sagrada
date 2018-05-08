package ingsw.model;

import ingsw.controller.Controller;
import ingsw.controller.network.socket.UserObserver;
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

    /* REMOTE SAGRADAGAME PART*/

    @Override
    public int getConnectedUsers() {
        return connectedUsers.size();
    }

    @Override
    public synchronized User loginUser(String username, UserObserver userObserver) throws InvalidUsernameException, RemoteException {
        User currentUser = new User(username);
        if (!connectedUsers.containsKey(username)) {
            currentUser.addListener(userObserver);
            connectedUsers.put(username, currentUser);
            broadcastUsersConnected(username);
            return connectedUsers.get(username);
        }
        throw new InvalidUsernameException("Username has been taken already");
    }

    @Override
    public void broadcastUsersConnected(String username) throws RemoteException {
        for (User user : connectedUsers.values()) {
            if (!user.getUsername().equals(username)) {
                System.out.println(user.getUsername());
                user.updateUserConnected(connectedUsers.size());
            }
        }
    }
}
