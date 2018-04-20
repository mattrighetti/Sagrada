package ingsw.model;

import java.util.LinkedList;
import java.util.List;

public class User {
    String username;
    int noOfWins;
    int noOfLose;
    int noOfDraws;
    List<String> matchesPlayed;

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
}
