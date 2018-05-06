package ingsw.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class RoundTrack {
    private Round currentRound;
    private List<Round> rounds;
    private List<Set<Dice>> unusedDice;

    public RoundTrack(List<Round> rounds) {
        this.rounds = rounds;
        this.unusedDice = new ArrayList<>();
    }

    public Round getCurrentRound() {
        return currentRound;
    }

    public void startMatch(Board board) {
        for (int i = 0; i < 10; i++) {
            currentRound = new Round(board);
        }
    }
}
