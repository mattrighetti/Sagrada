package ingsw.model;

import java.util.List;

public class Player {
    String username;
    int noOfWins;
    int noOfLose;
    int noOfDraws;
    List<String> matchesPlayed;

    public Player(String username) {
        this.username = username;
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
