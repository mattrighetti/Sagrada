package ingsw.model;

import ingsw.controller.Controller;
import ingsw.controller.network.commands.RequestHandler;
import ingsw.controller.network.socket.ServerController;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Map;

public class SagradaGame extends UnicastRemoteObject implements RemoteSagradaGame {
    private static SagradaGame sagradaGameSingleton;
    Map<String, Controller> matchesByName; // List of all open matches
    Map<String, User> connectedUsers; // List of connected users

    private SagradaGame() throws RemoteException {
        super();
    }

    public static SagradaGame get() throws RemoteException {
        if (sagradaGameSingleton == null) {
            sagradaGameSingleton = new SagradaGame();
        }

        return sagradaGameSingleton;
    }

    @Override
    public User loginUser(String username) {
        User currentUser = new User(username);
        if (!connectedUsers.containsKey(username)) {
            connectedUsers.put(username, currentUser);

            for (User user: connectedUsers.values()) {
                if (!user.getUsername().equals(username))
                    user.updateUserConnected(currentUser);
            }

            return connectedUsers.get(username);
        } else return null;
    }
}
