package ingsw.controller.network;

import ingsw.model.cards.patterncard.PatternCard;

public interface NetworkType {

    void loginUser(String username);

    void logoutUser();

    void createMatch(String matchName);

    void joinExistingMatch(String matchName);

    void choosePatternCard(PatternCard patternCard);

    void draftDice(String username);
}
