package ingsw.controller.network;

import ingsw.model.Dice;
import ingsw.model.cards.patterncard.PatternCard;

public interface NetworkType {

    void loginUser(String username);

    void logoutUser();

    void createMatch(String matchName);

    void joinExistingMatch(String matchName);

    void draftDice();

    void sendAck();

    void placeDice(Dice dice, int columnIndex, int rowIndex);

    void useToolCard(String string);

    void choosePatternCard(PatternCard patternCard);

    void endTurn();
}
