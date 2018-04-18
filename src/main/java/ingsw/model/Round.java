package ingsw.model;

import java.util.Set;

public class Round {

    private static int currentRound = 0;
    private static int noRound;
    private Set<Dice> unesuedDice;

    public Round(Set<Dice> unesuedDice) {
        currentRound++;
        noRound = currentRound;
        this.unesuedDice = unesuedDice;

    }

}
