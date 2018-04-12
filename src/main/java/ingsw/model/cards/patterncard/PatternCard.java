package ingsw.model.cards.patterncard;

import ingsw.model.cards.Card;

public abstract class PatternCard extends Card {
    private int difficulty;

    public PatternCard(int difficulty) {
        this.difficulty = difficulty;
    }
}
