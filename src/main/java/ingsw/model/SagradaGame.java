package ingsw.model;

import ingsw.controller.Controller;
import ingsw.controller.network.commands.CreateMatchResponse;
import ingsw.controller.network.socket.UserObserver;
import ingsw.exceptions.InvalidUsernameException;
import ingsw.utilities.Broadcaster;
import ingsw.utilities.DoubleString;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

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

    /**
     * Method that returns the number of users currently connected to SagradaGame
     * @return Number of users currently connected to SagradaGame
     */
    @Override
    public int getConnectedUsers() {
        return connectedUsers.size();
    }

    @Override
    public List<DoubleString> doubleStringBuilder() throws RemoteException {
        DoubleString doubleString;
        List<DoubleString> availableMatchesDoubleString = new ArrayList<>();
        for (Controller match : matchesByName.values()) {
            doubleString = new DoubleString(match.getMatchName(), match.getConnectedUsers());
            availableMatchesDoubleString.add(doubleString);
        }
        return availableMatchesDoubleString;
    }

    /**
     * Method that logs in the User to the main SagradaGame server
     * @param username Username of the username to log in
     * @param userObserver UserObserver of the User
     * @return Number of users currently connected to SagradaGame
     * @throws InvalidUsernameException
     * @throws RemoteException
     */
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

    /**
     * Method that lets the user create a match
     * @param matchName Name of the match to create
     * @return
     * @throws RemoteException
     */
    @Override
    public synchronized void createMatch(String matchName) throws RemoteException {
        Controller controller;
        if (!matchesByName.containsKey(matchName)) {
            controller = new Controller(matchName);
            matchesByName.put(matchName, controller);

            Registry registry = LocateRegistry.getRegistry();
            registry.rebind(matchName, controller);

            Broadcaster.broadcastResponseToAll(connectedUsers, new CreateMatchResponse(doubleStringBuilder()));
        } else
            throw new RemoteException("Match already exists");
    }

    @Override
    public void loginUserToController(String matchName, User user) throws RemoteException {
        matchesByName.get(matchName).loginUser(user);
        Broadcaster.broadcastResponse(connectedUsers, user.getUsername(), new CreateMatchResponse(doubleStringBuilder()));
    }

    public Controller getMatchController(String matchName) {
        return matchesByName.get(matchName);
    }

    /**
     * Method with which the user will join an existing match and it will return the match's controller
     * @param matchName Name of the match to join
     * @return
     * @throws RemoteException
     */
    @Override
    public synchronized Controller joinMatch(String matchName) throws RemoteException {
        if (matchesByName.containsKey(matchName)) {
            return matchesByName.get(matchName);
        } else
            throw new RemoteException("Match unreachable");
    }

    /**
     * Method that broadcasts a simple string message to every connected user except the one with the
     * name passed by parameter
     * @param username Username to exclude from the broadcast message
     * @throws RemoteException
     */
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
