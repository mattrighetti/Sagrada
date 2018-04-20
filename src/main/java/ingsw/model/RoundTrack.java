package ingsw.model;

import java.util.List;

public class RoundTrack {
    private List<Round> rounds;

    public RoundTrack(List<Round> rounds) {
        this.rounds = rounds;
    }

    public List<Round> getRounds() {
        return rounds;
    }
}
