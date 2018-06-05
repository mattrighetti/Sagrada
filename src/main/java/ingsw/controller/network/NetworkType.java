package ingsw.controller.network;

import ingsw.model.Dice;
import ingsw.model.cards.patterncard.PatternCard;
import ingsw.utilities.Tuple;

public interface NetworkType {

    void loginUser(String username);

    void logoutUser();

    void createMatch(String matchName);

    void joinExistingMatch(String matchName);

    void draftDice();

    void sendAck();

    void placeDice(Dice dice, int columnIndex, int rowIndex);

    void useToolCard(String string);

    void grozingPliersMove(Dice dice, boolean increase);

    void fluxBrushMove (Dice dice);

    void fluxRemoverMove(Dice dice);

    void copperFoilBurnisherMove(Tuple dicePosition, Tuple position);

    void choosePatternCard(PatternCard patternCard);

    void endTurn();

    void grindingStoneMove(Dice dice);
}
