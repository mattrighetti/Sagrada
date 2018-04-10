package ingsw;

import java.util.List;

public class Player {
    String name;
    int noOfWins;
    int noOfLose;
    int noOfDraws;
    List<String> matchesPlayed;

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
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
