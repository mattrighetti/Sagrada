package ingsw.utilities;

import ingsw.controller.network.commands.*;
import ingsw.controller.network.socket.UserObserver;
import ingsw.model.User;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Used to send Response to more than one User.
 */
public class UserBroadcaster {
    private static final String ERROR_MESSAGE ="Broadcaster is not active";
    private boolean isBroadcasterActive;
    private Map<String, User> users;

    /**
     * Set the Users' Map and activate the broadcaster
     * @param users connected Users
     */
    public UserBroadcaster(Map<String, User> users) {
        this.users = users;
        isBroadcasterActive = true;
    }

    /**
     * Creates a List of the active user by calling <code>user.getUserObserver().checkIfActive()</code> excluding the one passed as parameter
     * @param usernameToExclude User that won't receive the message
     * @return Active UserObserver
     */
    private List<UserObserver> usersToBroadcast(String usernameToExclude) {
        List<UserObserver> playerListToBroadcast = new ArrayList<>();
        for (User user : users.values()) {
            if (!user.getUsername().equals(usernameToExclude) && user.isActive()) {
                try {
                    user.getUserObserver().checkIfActive();
                    playerListToBroadcast.add(user.getUserObserver());
                } catch (RemoteException e) {
                    System.err.println("RMI Player " + user.getUsername() + " is not active, deactivating user");
                }
            }
        }
        return playerListToBroadcast;
    }

    /**
     * Creates a List of the active user by calling <code>user.getUserObserver().checkIfActive()</code>
     * @return Active UserObserver
     */
    private List<UserObserver> usersToBroadcast() {
        List<UserObserver> playerListToBroadcast = new ArrayList<>();
        for (User user : users.values()) {
            if (user.isActive()) {
                try {
                    user.getUserObserver().checkIfActive();
                    playerListToBroadcast.add(user.getUserObserver());
                } catch (RemoteException e) {
                    System.err.println("RMI Player " + user.getUsername() + " is not active, deactivating user");
                }
            }
        }
        return playerListToBroadcast;
    }

    /**
     * Broadcast a TripleString used for the Ranking
     * @param tripleStrings Ranking
     */
    public void broadcastResponseToAll(List<TripleString> tripleStrings) {
        if (isBroadcasterActive) {
            for (UserObserver user : usersToBroadcast()) {
                try {
                    user.sendResponse(new RankingDataResponse(tripleStrings));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        } else System.out.println(ERROR_MESSAGE);
    }

    /**
     * Used when a match is created
     * @param createMatchResponse Response to send
     */
    public void broadcastResponseToAll(CreateMatchResponse createMatchResponse) {
        if (isBroadcasterActive) {
            for (User user : users.values()) {
                if (user.isActive()) {
                    try {
                        user.getUserObserver().sendResponse(createMatchResponse);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else System.out.println(ERROR_MESSAGE);
    }

    /**
     * Used when a match is created
     * @param usernameToExclude User that won't receive the message
     * @param createMatchResponse Response to send
     */
    public void broadcastResponse(String usernameToExclude, CreateMatchResponse createMatchResponse) {
        if (isBroadcasterActive) {
            for (UserObserver userObserver : usersToBroadcast(usernameToExclude)) {
                try {
                    userObserver.sendResponse(createMatchResponse);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        } else System.out.println(ERROR_MESSAGE);
    }

    /**
     * Broadcast the number of connnected users
     * @param numberOfConnectedUsers Number of connected users
     */
    public void broadcastResponseToAll(int numberOfConnectedUsers) {
        if (isBroadcasterActive) {
            for (UserObserver userObserver : usersToBroadcast()) {
                try {
                    userObserver.onJoin(numberOfConnectedUsers);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        } else System.out.println(ERROR_MESSAGE);
    }
}
