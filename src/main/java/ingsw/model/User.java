package ingsw.model;

import ingsw.controller.network.socket.JoinedUserObserver;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class User implements Serializable {
    private String username;
    private transient JoinedUserObserver joinedUserObserver;
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

    public void addListener(JoinedUserObserver joinedUserObserver) {
        this.joinedUserObserver = joinedUserObserver;
    }

    public void updateUserConnected(User user) {
        joinedUserObserver.onJoin(user);
    }
}
