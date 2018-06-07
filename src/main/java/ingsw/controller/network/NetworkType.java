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

    void fluxBrushMove(Dice selectedDice, int columnIndex, int rowIndex);

    void fluxBrushMove();

    void fluxRemoverMove(Dice dice);

    void copperFoilBurnisherMove(Tuple dicePosition, Tuple position);

    void corkBackedStraightedgeMove(Dice selectedDice, int row, int column);

    void lathekinMove(Tuple dicePosition, Tuple position, boolean doubleMove);

    void choosePatternCard(PatternCard patternCard);

    void endTurn();

    void grindingStoneMove(Dice dice);

    void lensCutter(int roundIndex, String roundTrackDice, String poolDice);

}
