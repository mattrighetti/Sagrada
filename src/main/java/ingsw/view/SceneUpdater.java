package ingsw.view;

import ingsw.controller.network.NetworkType;
import ingsw.controller.network.commands.*;
import ingsw.model.Dice;
import ingsw.model.cards.patterncard.PatternCard;
import ingsw.utilities.DoubleString;
import ingsw.utilities.MoveStatus;
import ingsw.utilities.TripleString;

import java.util.List;
import java.util.Map;

/**
 * Interface implemented by every FXML Controller to control what's going to be displayed on the View.
 * If a class does not implement a method in the interface this will print out a message "Not overridden" on the terminal.
 */
public interface SceneUpdater {

    /**
     * Method that should update the number of users connected to the game
     *
     * @param usersConnected number of connected users
     */
    default void updateConnectedUsers(int usersConnected) {
        System.out.println("Not overridden -> updateConnectedUsers");
    }

    default void showSelectedMatchHistory(List<MoveStatus> history) {
        System.out.println("Not overridden -> showSelectedMatchHistory");
    }

    /**
     * Method that should pass the type of connection currently in use (RMI or Socket)
     *
     * @param networkType object that does implements the Network type interface
     */
    default void setNetworkType(NetworkType networkType) {
        System.out.println("Not overridden -> setNetworkType");
    }

    /**
     * Method that should update the matches available in the View
     *
     * @param matches matches currently available in the game
     */
    default void updateExistingMatches(List<DoubleString> matches) {
        System.out.println("Not overridden -> updateExistingMatches");
    }

    /**
     * Method that does set the specified PatternCard to the user
     *
     * @param patternCards selected PatternCard
     */
    default void setPatternCards(List<PatternCard> patternCards) {
        System.out.println("Not overridden -> setPatternCards");
    }

    /**
     * Method that launches the Second GUI
     *
     * @param username player's username
     */
    default void launchSecondGui(String username) {
        System.out.println("Not overridden -> launchSecondGui");
    }

    default void launchAlert() {
        System.out.println("Not overridden -> launchAlert");
    }

    /**
     * Method that should close the current window
     */
    default void closeStage() {
        System.out.println("Not overridden -> closeStage");
    }

    default void updateRankingStatsTableView(List<TripleString> tripleStringList) {
        System.out.println("Not overridden -> updateRankingStatsTableView");
    }

    /**
     * Method that should launch the Third GUI
     *
     * @param patternCardNotification PatternCards to be displayed in the ThirdGUI
     */
    default void launchThirdGui(PatternCardNotification patternCardNotification) {
        System.out.println("Not overridden -> launchThirdGui");
    }

    /**
     * Method that loads the data received at the beginning of the match
     *
     * @param boardDataResponse class that contains every object needed to play the game
     */
    default void loadData(BoardDataResponse boardDataResponse) {
        System.out.println("Not overridden -> loadData");
    }

    /**
     * Method that launches the Fourth GUI
     *
     * @param boardDataResponse class that contains every object needed to play the game
     */
    default void launchFourthGui(BoardDataResponse boardDataResponse) {
        System.out.println("Not overridden -> launchFourthGui");
    }

    /**
     * Method that notifies the user that it's his turn to draft the dice
     */
    default void popUpDraftNotification() {
        System.out.println("Not overridden -> popUpDraftNotification");
    }

    /**
     * Method that displays the drafted dice on the View
     *
     * @param dice drafted dice
     */
    default void setDraftedDice(List<Dice> dice) {
        System.out.println("Not overridden -> setDraftedDice");
    }

    /**
     * Method that sets the available positions in the View
     *
     * @param startTurnNotification start turn notification
     */
    default void startTurn(StartTurnNotification startTurnNotification) {
        System.out.println("Not overridden -> startTurn");
    }

    /**
     * Generic method that does update the entire View with the current game state
     *
     * @param updateViewResponse class that contains updated object of the game
     */
    default void updateView(UpdateViewResponse updateViewResponse) {
        System.out.println("Not overridden -> updateView");
    }

    /**
     * Method that update the round track with the turn remaining dice at the end of the round
     *
     * @param roundTrackNotification class that contains the updated Round Track List
     */
    default void updateRoundTrack(RoundTrackNotification roundTrackNotification) {
        System.out.println("Not overridden -> updateRoundTrack");
    }

    default void updateMovesHistory(MoveStatusNotification notification) {
        System.out.println("Not overridden -> updateMovesHistory");
    }

    default void toolCardAction(DraftedDiceToolCardResponse draftedDiceToolCardResponse) {
        System.out.println("Not overridden -> toolCardAction");
    }

    default void toolCardAction(GrozingPliersResponse useToolCardResponse) {
        System.out.println("Not overridden -> toolCardAction");
    }

    default void toolCardAction(FluxBrushResponse useToolCardResponse) {
        System.out.println("Not overridden -> toolCardAction");
    }

    default void toolCardAction(FluxRemoverResponse useToolCardResponse) {
        System.out.println("Not overridden -> toolCardAction");
    }

    default void toolCardAction(GrindingStoneResponse useToolCardResponse) {
        System.out.println("Not overridden -> toolCardAction");
    }

    default void toolCardAction(CopperFoilBurnisherResponse useToolCardResponse) {
        System.out.println("Not overridden -> toolCardAction");
    }

    default void toolCardAction(LathekinResponse useToolCardResponse) {
        System.out.println("Not overridden -> toolCardAction");
    }

    default void toolCardAction(CorkBackedStraightedgeResponse useToolCardResponse) {
        System.out.println("Not overridden -> toolCardAction");
    }

    default void toolCardAction(LensCutterResponse useToolCardResponse) {
        System.out.println("Not overridden -> toolCardAction");
    }

    default void toolCardAction(EglomiseBrushResponse useToolCardResponse) {
        System.out.println("Not overridden -> toolCardAction");
    }

    default void toolCardAction(RoundTrackToolCardResponse useToolCardResponse) {
        System.out.println("Not overridden -> toolCardAction");
    }

    default void toolCardAction(PatternCardToolCardResponse useToolCardResponse) {
        System.out.println("Not overridden -> toolCardAction");
    }

    default void toolCardAction(AvoidToolCardResponse useToolCardResponse) {
        System.out.println("Not overridden -> toolCardAction");
    }

    default void toolCardAction(RunningPliersResponse useToolCardResponse) {
        System.out.println("Not overridden -> toolCardAction");
    }

    default void toolCardAction(TapWheelResponse useToolCardResponse) {
        System.out.println("Not overridden -> toolCardAction");
    }

    default void launchProgressForm() {
        System.out.println("Not overridden -> launchProgressForm");
    }

    default void setUsernameInApplication(String username) {
        System.out.println("Not overridden -> setUsernameInApplication");
    }

    default void showLostNotification(int totalScore) {
        System.out.println("Not overridden -> showLostNotification");
    }

    default void showWinnerNotification(int totalScore) {
        System.out.println("Not overridden -> showWinnerNotification");
    }

    default void timeOut(TimeOutResponse timeOutResponse) {
        System.out.println("Not overridden");
    }

    default void loadLobbyData(BundleDataResponse bundleDataResponse) {
        System.out.println("Not overridden -> loadLobbyData");
    }

    default void showFinishedMatches(List<String> finishedMatches) {
        System.out.println("Not overridden -> showFinishedMatches");
    }

    default void endedTurn() {
        System.out.println("Not overridden -> endedTurn");
    }

    default void setAvailablePositions(Map<String, Boolean[][]> availablePositions) {
        System.out.println("Not overridden -> setAvailablePositions");
    }
}
