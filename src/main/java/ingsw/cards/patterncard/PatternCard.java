package ingsw.cards.patterncard;

import ingsw.cards.Card;

public abstract class PatternCard extends Card {
    private int difficulty;

    public PatternCard(int difficulty) {
        this.difficulty = difficulty;
    }
}
