package ingsw.model;

import ingsw.controller.network.socket.UserObserver;

import java.io.Serializable;
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
        this.active = false;
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
        return userObserver;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }

    void updateUserConnected(int numberOfConnectedUsers) throws RemoteException {
        userObserver.onJoin(numberOfConnectedUsers);
    }
}
