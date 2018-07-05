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

    /**
     * Response that notifies the user that the chosen username has already been taken
     */
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
     * Method that update the round track with the remaining dice at the end of the round
     *
     * @param roundTrackNotification class that contains the updated Round Track List
     */
    default void updateRoundTrack(RoundTrackNotification roundTrackNotification) {
        System.out.println("Not overridden -> updateRoundTrack");
    }

    /**
     * Method that update the move history
     *
     * @param notification the notification containing the new move
     */
    default void updateMovesHistory(MoveStatusNotification notification) {
        System.out.println("Not overridden -> updateMovesHistory");
    }

    /**
     * Method that update the drafted dice after a ToolCard move
     *
     * @param draftedDiceToolCardResponse contains data for the toolCard move
     */
    default void toolCardAction(DraftedDiceToolCardResponse draftedDiceToolCardResponse) {
        System.out.println("Not overridden -> toolCardAction");
    }

    /**
     * Response for Tool Card move: Grozing Pliers
     * @param useToolCardResponse contains data for the toolCard move
     */
    default void toolCardAction(GrozingPliersResponse useToolCardResponse) {
        System.out.println("Not overridden -> toolCardAction");
    }

    /**
     *  Response for Tool Card move: FluxBrush
     * @param useToolCardResponse contains data for the toolCard move
     */
    default void toolCardAction(FluxBrushResponse useToolCardResponse) {
        System.out.println("Not overridden -> toolCardAction");
    }

    /**
     *  Response for Tool Card move: FluxRemover
     * @param useToolCardResponse contains data for the toolCard move
     */
    default void toolCardAction(FluxRemoverResponse useToolCardResponse) {
        System.out.println("Not overridden -> toolCardAction");
    }

    /**
     *  Response for Tool Card move: GrindingStone
     * @param useToolCardResponse contains data for the toolCard move
     */
    default void toolCardAction(GrindingStoneResponse useToolCardResponse) {
        System.out.println("Not overridden -> toolCardAction");
    }

    /**
     *  Response for Tool Card move: CopperFoilBurnisher
     * @param useToolCardResponse contains data for the toolCard move
     */
    default void toolCardAction(CopperFoilBurnisherResponse useToolCardResponse) {
        System.out.println("Not overridden -> toolCardAction");
    }

    /**
     *  Response for Tool Card move: Lathekin
     * @param useToolCardResponse contains data for the toolCard move
     */
    default void toolCardAction(LathekinResponse useToolCardResponse) {
        System.out.println("Not overridden -> toolCardAction");
    }

    /**
     *  Response for Tool Card move: CorkBackedStraightedge
     * @param useToolCardResponse contains data for the toolCard move
     */
    default void toolCardAction(CorkBackedStraightedgeResponse useToolCardResponse) {
        System.out.println("Not overridden -> toolCardAction");
    }

    /**
     *  Response for Tool Card move: LensCutter
     * @param useToolCardResponse contains data for the toolCard move
     */
    default void toolCardAction(LensCutterResponse useToolCardResponse) {
        System.out.println("Not overridden -> toolCardAction");
    }

    /**
     *  Response for Tool Card move: EglomiseBrush
     * @param useToolCardResponse contains data for the toolCard move
     */
    default void toolCardAction(EglomiseBrushResponse useToolCardResponse) {
        System.out.println("Not overridden -> toolCardAction");
    }

    /**
     * Method that update the round track after a tool card move that modifies it
     * @param useToolCardResponse contains data for the toolCard move
     */
    default void toolCardAction(RoundTrackToolCardResponse useToolCardResponse) {
        System.out.println("Not overridden -> toolCardAction");
    }

    /**
     * Method that update the PatternCard after a toolCard move
     * @param useToolCardResponse contains datas for the toolCard move
     */
    default void toolCardAction(PatternCardToolCardResponse useToolCardResponse) {
        System.out.println("Not overridden -> toolCardAction");
    }

    /**
     * Response that advices the player that he cannot use the tool card because of restriction or because
     * the player has not enough favour tokens
     * @param useToolCardResponse contains the favour token of the player
     */
    default void toolCardAction(AvoidToolCardResponse useToolCardResponse) {
        System.out.println("Not overridden -> toolCardAction");
    }

    /**
     * Response for Tool Card move: RunningPliers
     * @param useToolCardResponse contains data for the toolCard mov
     */
    default void toolCardAction(RunningPliersResponse useToolCardResponse) {
        System.out.println("Not overridden -> toolCardAction");
    }

    /**
     * Response for Tool Card move: TapWheel
     * @param useToolCardResponse contains data for the toolCard mov
     */
    default void toolCardAction(TapWheelResponse useToolCardResponse) {
        System.out.println("Not overridden -> toolCardAction");
    }

    /**
     * Response to launch the waiting progress in the GUI after the login
     */
    default void launchProgressForm() {
        System.out.println("Not overridden -> launchProgressForm");
    }

    /**
     * After the login request the server sends a response to confirm the login with the username chosen
     * @param username username chosen from the user
     */
    default void setUsernameInApplication(String username) {
        System.out.println("Not overridden -> setUsernameInApplication");
    }

    /**
     * Notification for the players who lose tha match
     * @param totalScore the player's score
     */
    default void showLostNotification(int totalScore) {
        System.out.println("Not overridden -> showLostNotification");
    }

    /**
     * Notification for the player who wins the match
     * @param totalScore the player's score
     */
    default void showWinnerNotification(int totalScore) {
        System.out.println("Not overridden -> showWinnerNotification");
    }

    /**
     * Notification that advices the player that the turn time is finished and the turn is ended
     * @param timeOutResponse it may contain match data to set in the client
     */
    default void timeOut(TimeOutResponse timeOutResponse) {
        System.out.println("Not overridden");
    }

    /**
     * Method that loads the ranking, the statistics, and the matches in the lobby view
     * @param bundleDataResponse
     */
    default void loadLobbyData(BundleDataResponse bundleDataResponse) {
        System.out.println("Not overridden -> loadLobbyData");
    }

    /**
     * Method that sends to the client the list of finished matches in Sagrada
     * @param finishedMatches list of finished matches to replay
     */
    default void showFinishedMatches(List<String> finishedMatches) {
        System.out.println("Not overridden -> showFinishedMatches");
    }

    /**
     * Method that notifies the player that his turn is ended and disables the commands in the view
     */
    default void endedTurn() {
        System.out.println("Not overridden -> endedTurn");
    }

    /**
     * It sends the new available positions to the clients
     * @param availablePositions the uploaded available positions uploaded
     */
    default void setAvailablePositions(Map<String, Boolean[][]> availablePositions) {
        System.out.println("Not overridden -> setAvailablePositions");
    }

    default void disconnectUser() {
        System.out.println("Not overridden -> disconnectUser()");
    }
}
