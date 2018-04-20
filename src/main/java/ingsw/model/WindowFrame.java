package ingsw.model;

import ingsw.model.cards.patterncard.PatternCard;

public class WindowFrame {
    private final PatternCard patternCard;

    public WindowFrame(PatternCard patternCard) {
        this.patternCard = patternCard;
    }

    public PatternCard getPatternCard() {
        return patternCard;
    }
}
