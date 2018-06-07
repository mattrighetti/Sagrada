package ingsw.model;

import ingsw.controller.network.socket.UserObserver;

import java.io.Serializable;
import java.rmi.ConnectException;
import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;

/**
 * Class that identifies the "client" until the game started, after the game has started it will be contained in
 * the Player class
 */
public class User implements Serializable {
    private String username;
    private boolean active;
    private UserObserver userObserver;
    private int noOfWins;
    private int noOfLose;
    private int noOfDraws;
    private List<String> matchesPlayed;

    public User(String username) {
        this.username = username;
        this.active = true;
        matchesPlayed = new LinkedList<>();
    }

    public String getUsername() {
        return username;
    }

    int getNoOfWins() {
        return noOfWins;
    }

    int getNoOfLose() {
        return noOfLose;
    }

    int getNoOfDraws() {
        return noOfDraws;
    }

    List<String> getMatchesPlayed() {
        return matchesPlayed;
    }

    public void addListener(UserObserver userObserver) {
        this.userObserver = userObserver;
    }

    public UserObserver getUserObserver() {
        try {
            userObserver.checkIfActive();
            return userObserver;
        } catch (RemoteException e) {
            setActive(false);
            return null;
        }
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    boolean isActive() {
        return active;
    }

    // TODO consider removing this and adding a method to the Broadcaster
    void updateUserConnected(int numberOfConnectedUsers) throws RemoteException {
        userObserver.onJoin(numberOfConnectedUsers);
    }
}
