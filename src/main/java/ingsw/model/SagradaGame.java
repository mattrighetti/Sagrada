package ingsw.model;

import ingsw.controller.Controller;
import ingsw.controller.network.commands.CreateMatchResponse;
import ingsw.controller.network.commands.LoginUserResponse;
import ingsw.controller.network.commands.ReJoinResponse;
import ingsw.controller.network.socket.UserObserver;
import ingsw.exceptions.InvalidUsernameException;
import ingsw.utilities.Broadcaster;
import ingsw.utilities.DoubleString;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
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
     *
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
     *
     * @param username     Username of the username to log in
     * @param userObserver UserObserver of the User
     * @return Number of users currently connected to SagradaGame
     * @throws InvalidUsernameException if the username has already been taken
     * @throws RemoteException          if something with the network is wrong
     */
    @Override
    public synchronized User loginUser(String username, UserObserver userObserver) throws InvalidUsernameException, RemoteException {

        // Check if the username is present and inactive
        if (connectedUsers.containsKey(username) && !connectedUsers.get(username).isActive()) {
            // Update the UserObserver
            connectedUsers.get(username).addListener(userObserver);
            connectedUsers.get(username).setActive(true);
            //Check in which match the user was playing before disconnecting
            for (Controller controller : matchesByName.values()) {
                for (Player player : controller.getPlayerList()) {
                    if (player.getPlayerUsername().equals(username)) {
                        player.getUser().addListener(userObserver);
                        player.getUserObserver().sendResponse(new ReJoinResponse(controller.getMatchName(), player.getPlayerUsername()));
                    }
                }
            }
            // Return the same user with the updated UserObserver
            return connectedUsers.get(username);
        }

        // In case there is no username | the username is active
        User currentUser = new User(username);
        if (!connectedUsers.containsKey(username)) {
            currentUser.addListener(userObserver);
            connectedUsers.put(username, currentUser);
            connectedUsers.get(username).getUserObserver()
                    .sendResponse(new LoginUserResponse(currentUser, getConnectedUsers(), doubleStringBuilder()));
            broadcastUsersConnected(username);
            return connectedUsers.get(username);
        }

        throw new InvalidUsernameException("Username has been taken already");
    }

    @Override
    public void logoutUser(String username) throws RemoteException {
        connectedUsers.remove(username);
        broadcastUsersConnected(username);

        if (connectedUsers.get(username) != null) {
            throw new RemoteException("Logout unsuccessful");
        }
    }

    /**
     * Method that lets the user create a match
     *
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

            try {
                Naming.rebind("rmi://localhost:1099/" + matchName, controller);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            Broadcaster.broadcastResponseToAll(connectedUsers, new CreateMatchResponse(doubleStringBuilder()));
        } else
            throw new RemoteException("Match already exists");
    }

    @Override
    public void loginUserToController(String matchName, String username) throws RemoteException {
        for (User user : connectedUsers.values()) {
            if (user.getUsername().equals(username)) {
                matchesByName.get(matchName).loginUser(user);
                Broadcaster.broadcastResponse(connectedUsers, user.getUsername(), new CreateMatchResponse(doubleStringBuilder()));
            }
        }
    }

    @Override
    public void loginPrexistentPlayer(String matchName, User newUser) throws RemoteException {
        for (User user : connectedUsers.values()) {
            if (user.getUsername().equals(newUser.getUsername())) {
                connectedUsers.remove(user);
                connectedUsers.put(newUser.getUsername(), newUser);
            }
        }

        for (Player player : matchesByName.get(matchName).getPlayerList()) {
            if (player.getPlayerUsername().equals(newUser.getUsername()) && !player.getUser().isActive()) {
                System.out.println("SagradaGame: re-activating User " + newUser.getUsername());
                player.updateUser(newUser);
                System.out.println("Player has been updated, it's now back online");
            }
        }
    }

    public Controller getMatchController(String matchName) {
        return matchesByName.get(matchName);
    }

    /**
     * Method that broadcasts a simple string message to every connected user except the one with the
     * name passed by parameter
     *
     * @param username Username to exclude from the broadcast message
     * @throws RemoteException
     */
    @Override
    public void broadcastUsersConnected(String username) {
        Broadcaster.broadcastResponseToAll(connectedUsers, connectedUsers.size());
    }

    @Override
    public void deactivateUser(User disconnectedUser) throws RemoteException {
        for (User user : connectedUsers.values()) {
            if (user.getUsername().equals(disconnectedUser.getUsername())) {
                user.setActive(false);
            }
        }
    }
}
