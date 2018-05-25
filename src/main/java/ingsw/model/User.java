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
    private UserObserver userObserver;
    private int noOfWins;
    private int noOfLose;
    private int noOfDraws;
    private List<String> matchesPlayed;

    public User(String username) {
        this.username = username;
        matchesPlayed = new LinkedList<>();
    }

    public String getUsername() {
        return username;
    }

    public int getNoOfWins() {
        return noOfWins;
    }

    public int getNoOfLose() {
        return noOfLose;
    }

    public int getNoOfDraws() {
        return noOfDraws;
    }

    public List<String> getMatchesPlayed() {
        return matchesPlayed;
    }

    public void addListener(UserObserver userObserver) {
        this.userObserver = userObserver;
    }

    public UserObserver getUserObserver() {
        return userObserver;
    }

    public void updateUserConnected(int numberOfConnectedUsers) throws RemoteException {
        userObserver.onJoin(numberOfConnectedUsers);
    }
}
