package ingsw.utilities;

import ingsw.controller.network.commands.*;
import ingsw.controller.network.socket.UserObserver;
import ingsw.model.User;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserBroadcaster {
    private static final String ERROR_MESSAGE ="Broadcaster is not active";
    private boolean isBroadcasterActive;
    private Map<String, User> users;

    public UserBroadcaster(Map<String, User> users) {
        this.users = users;
        isBroadcasterActive = true;
    }

    public void disableBroadcaster() {
        isBroadcasterActive = false;
    }

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
