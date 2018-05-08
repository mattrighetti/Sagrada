package ingsw.view;

import ingsw.model.cards.patterncard.PatternCard;

import java.util.Set;

public interface RemoteView {
    void displayPatternCardsToChoose(Set<PatternCard> patternCards);
}
