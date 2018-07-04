package ingsw.controller.network;

import ingsw.model.Dice;
import ingsw.model.cards.patterncard.PatternCard;
import ingsw.utilities.Tuple;
import ingsw.view.SceneUpdater;

public interface NetworkType {

    void setSceneUpdater(SceneUpdater sceneUpdater);

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

    void fluxBrushMove(Dice selectedDice, int rowIndex, int columnIndex);

    void fluxBrushMove();

    void fluxRemoverMove(Dice dice);

    void fluxRemoverMove(Dice dice, int chosenValue);

    void fluxRemoverMove();

    void fluxRemoverMove(Dice selectedDice, int rowIndex, int columnIndex);

    void copperFoilBurnisherMove(Tuple dicePosition, Tuple position);

    void corkBackedStraightedgeMove(Dice selectedDice, int row, int column);

    void lathekinMove(Tuple dicePosition, Tuple position, boolean doubleMove);

    void choosePatternCard(PatternCard patternCard);

    void endTurn(String player);

    void grindingStoneMove(Dice dice);

    void lensCutter(int roundIndex, String roundTrackDice, String poolDice);

    void runningPliersMove(Dice selectedDice, int rowIndex, int columnIndex);

    void tapWheelMove(Dice dice, int phase);

    void tapWheelMove(Tuple dicePosition, Tuple position, int phase, boolean doubleMove);

    void tapWheelMove(int endTapWheel);

    void requestHistory(String matchName);

    void requestBundleData();

    void requestFinishedMatches();

    void disconnectUser();
}
