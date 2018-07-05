package ingsw.controller.network.rmi;

import ingsw.controller.network.NetworkType;
import ingsw.controller.network.commands.*;
import ingsw.model.Dice;
import ingsw.model.cards.patterncard.PatternCard;
import ingsw.utilities.Tuple;
import ingsw.view.SceneUpdater;

import java.rmi.RemoteException;

/**
 * Class that receive calls through the networkType from the view
 * and calls(like socket) the method handle on the RMIUserObserver;
 */
public class RMIController implements ResponseHandler, NetworkType {
    private RMIHandler rmiHandler;
    private RMIUserObserver rmiUserObserver;
    private Response response;
    private SceneUpdater sceneUpdater;

    /**
     * Set the ip address and the RMIUserObserver
     *
     * @param ipAddress address
     */
    public void connect(String ipAddress) {
        try {
            rmiUserObserver = new RMIUserObserver(this);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        rmiHandler = new RMIHandler(this, rmiUserObserver, ipAddress);
    }

    /**
     * Set the scene updatedr
     *
     * @param sceneUpdater FXML Controller
     */
    @Override
    public void setSceneUpdater(SceneUpdater sceneUpdater) {
        this.sceneUpdater = sceneUpdater;
    }


    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /* NETWORK TYPE PART */
    /* METHODS EXECUTED BY THE VIEW TO MAKE ACTIONS */


    /**
     * Method that logs in the user to SagradaGame
     *
     * @param username username chosen by the user
     */
    @Override
    public void loginUser(String username) {
        new LoginUserRequest(username).handle(rmiHandler);
    }

    /**
     * Method that creates a match
     *
     * @param matchName name of the match to create
     */
    @Override
    public void createMatch(String matchName) {
        new CreateMatchRequest(matchName).handle(rmiHandler);
    }

    /**
     * Method that logs the user into the match
     *
     * @param matchName name of the match to join
     */
    @Override
    public void joinExistingMatch(String matchName) {
        response = new JoinMatchRequest(matchName).handle(rmiHandler);
        response.handle(this);
    }

    /**
     * Request the bundle data
     */
    @Override
    public void requestBundleData() {
        new BundleDataRequest().handle(rmiHandler);
    }

    /**
     * Method that sets the chosen pattern card to the game model
     *
     * @param patternCard pattern card chosen by the player
     */
    @Override
    public void choosePatternCard(PatternCard patternCard) {
        new ChosenPatternCardRequest(patternCard).handle(rmiHandler);
    }

    /**
     * Method that drafts dice from the board
     */
    @Override
    public void draftDice() {
        new DraftDiceRequest().handle(rmiHandler);
    }

    /**
     * Method that sends an acknowledgement to the server-side whenever it's needed
     */
    @Override
    public void sendAck() {
        new Ack().handle(rmiHandler);
    }

    /**
     * Method that tells the server to position a dice in the user's pattern card at that column and row indexes.
     * It's one of the possible move that the player is able to use
     *
     * @param dice        dice placed in the window
     * @param columnIndex column index where the die has been placed
     * @param rowIndex    row index where the die has been placed
     */
    @Override
    public void placeDice(Dice dice, int columnIndex, int rowIndex) {
        new PlaceDiceRequest(dice, columnIndex, rowIndex).handle(rmiHandler);
    }

    /**
     * Method that tells to the Server that the player wants to use a toolcard
     *
     * @param toolCardName
     */
    @Override
    public void useToolCard(String toolCardName) {
        new UseToolCardRequest(toolCardName).handle(rmiHandler);
    }

    /**
     * Method that creates the request for Gronzing Pliers
     *
     * @param dice Selected dice
     * @param increase Increase/decrease value
     */
    @Override
    public void grozingPliersMove(Dice dice, boolean increase) {
        new GrozingPliersRequest(dice, increase).handle(rmiHandler);
    }

    /**
     * Method that creates the request for Flux Brush
     *
     * @param dice Selected dice
     */
    @Override
    public void fluxBrushMove(Dice dice) {
        new FluxBrushRequest(dice).handle(rmiHandler);
    }

    /**
     * Method that creates the request for Flux Brush
     *
     * @param selectedDice Selected dice
     * @param rowIndex Row index
     * @param columnIndex Column index
     */
    @Override
    public void fluxBrushMove(Dice selectedDice, int rowIndex, int columnIndex) {
        new FluxBrushRequest(selectedDice, rowIndex, columnIndex).handle(rmiHandler);
    }

    /**
     * Method that creates the request for Flux Brush
     */
    @Override
    public void fluxBrushMove() {
        new FluxBrushRequest().handle(rmiHandler);
    }

    /**
     * Method that creates the request for Flux Remover
     *
     * @param dice Selected dice
     */
    @Override
    public void fluxRemoverMove(Dice dice) {
        new FluxRemoverRequest(dice).handle(rmiHandler);
    }

    /**
     * Method that creates the request for Flux Remover
     *
     * @param dice Selected dice
     * @param chosenValue new face up value
     */
    @Override
    public void fluxRemoverMove(Dice dice, int chosenValue) {
        new FluxRemoverRequest(dice, chosenValue).handle(rmiHandler);
    }


    /**
     * Method that creates the request for Flux Remover
     */
    @Override
    public void fluxRemoverMove() {
        new FluxBrushRequest().handle(rmiHandler);
    }

    /**
     * Method that creates the request for Flux Remover
     *
     * @param selectedDice Selected dice
     * @param columnIndex Column index
     * @param rowIndex Row index
     */
    @Override
    public void fluxRemoverMove(Dice selectedDice, int columnIndex, int rowIndex) {
        new FluxBrushRequest(selectedDice, rowIndex, columnIndex).handle(rmiHandler);
    }

    /**
     * Method that creates the request for Copper Foil Burnisher
     *
     * @param dicePosition Initial dice position
     * @param position Final dice position
     */
    @Override
    public void copperFoilBurnisherMove(Tuple dicePosition, Tuple position) {
        new CopperFoilBurnisherRequest(dicePosition, position).handle(rmiHandler);
    }

    /**
     * Method that creates the request for corkBackedStraightEdge
     *
     * @param selectedDice Selected dice
     * @param row Row index
     * @param column Column index
     */
    @Override
    public void corkBackedStraightedgeMove(Dice selectedDice, int row, int column) {
        new CorkBackedStraightedgeRequest(selectedDice, row, column).handle(rmiHandler);
    }

    /**
     * Method that creates the request for Grinding Stone
     *
     * @param dice Selected dice
     */
    @Override
    public void grindingStoneMove(Dice dice) {
        new GrindingStoneRequest(dice).handle(rmiHandler);
    }

    /**
     * Method that creates the request for Lens Cutter
     *
     * @param roundIndex Round index
     * @param roundTrackDice Roundtrack dice
     * @param poolDice PoolDice
     */
    @Override
    public void lensCutter(int roundIndex, String roundTrackDice, String poolDice) {
        new LensCutterRequest(roundIndex, roundTrackDice, poolDice).handle(rmiHandler);
    }

    /**
     * Method that creates the request for Running Pliers
     *
     * @param selectedDice Selected Dice to place
     * @param rowIndex Row index
     * @param columnIndex Column index
     */
    @Override
    public void runningPliersMove(Dice selectedDice, int rowIndex, int columnIndex) {
        new RunningPliersRequest(selectedDice, rowIndex, columnIndex).handle(rmiHandler);
    }

    /**
     * Method that creates the request for Lathekin
     *
     * @param dicePosition Initial dice position
     * @param position Final dice position or second dice position in case of double move
     * @param doubleMove DoubleMove
     */
    @Override
    public void lathekinMove(Tuple dicePosition, Tuple position, boolean doubleMove) {
        new LathekinRequest(dicePosition, position, doubleMove).handle(rmiHandler);
    }

    /**
     * Method that creates the request for Tap Wheel
     *
     * @param dice Dice from the round track
     * @param phase Phase of Tap Wheel
     */
    @Override
    public void tapWheelMove(Dice dice, int phase) {
        new TapWheelRequest(dice, phase).handle(rmiHandler);
    }

    /**
     * Method that creates the request for Tap Wheel
     *
     * @param endTapWheel End phase Tap Wheel
     */
    @Override
    public void tapWheelMove(int endTapWheel) {
        new TapWheelRequest(endTapWheel).handle(rmiHandler);
    }

    /**
     * Method that creates the request for Tap Wheel
     *
     * @param dicePosition Dice initial position
     * @param position Dice final position or second dice position(in case of double move)
     * @param phase Phase of Tap Wheel
     * @param doubleMove DoubleMove
     */
    @Override
    public void tapWheelMove(Tuple dicePosition, Tuple position, int phase, boolean doubleMove) {
        new TapWheelRequest(dicePosition, position, phase, doubleMove).handle(rmiHandler);
    }

    /**
     * Method that creates the request for the history
     *
     * @param matchName Name of the match
     */
    @Override
    public void requestHistory(String matchName) {
        new ReadHistoryRequest(matchName).handle(rmiHandler);
    }

    /**
     * Method that creates the request for the finished matches
     */
    @Override
    public void requestFinishedMatches() {
        new FinishedMatchesRequest().handle(rmiHandler);
    }

    /**
     * Method that creates the request for ending the turn
     *
     * @param player Current player
     */
    @Override
    public void endTurn(String player) {
        new EndTurnRequest(player).handle(rmiHandler);
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /* HANDLER PART */
    /* METHOD EXECUTED BY THE RMI HANDLER */


    /**
     * Handle method for login User response
     *
     * @param loginUserResponse Response with a user and Launch the second gui
     */
    @Override
    public void handle(LoginUserResponse loginUserResponse) {
        if (loginUserResponse.user != null) {
            loginUserResponse.user.attachUserObserver(rmiUserObserver);
            System.out.println("New connection >>> " + loginUserResponse.user.getUsername());

            sceneUpdater.launchSecondGui(loginUserResponse.user.getUsername());
        } else
            sceneUpdater.launchAlert();
    }

    /**
     *  Handle method for logout User response
     *
     * @param logoutResponse Response that launch an alert
     */
    @Override
    public void handle(LogoutResponse logoutResponse) {
        if (logoutResponse != null) {
            sceneUpdater.closeStage();
        } else
            sceneUpdater.launchAlert();
    }

    /**
     * Handle method for integerResponse
     *
     * @param integerResponse Number of connected users
     */
    @Override
    public void handle(IntegerResponse integerResponse) {
        System.out.println("Connected Users: " + integerResponse.number);

        sceneUpdater.updateConnectedUsers(integerResponse.number);
    }

    /**
     * Handle method for createMatchResponse
     *
     * @param createMatchResponse Contains alla the existing matches
     */
    @Override
    public void handle(CreateMatchResponse createMatchResponse) {
        if (createMatchResponse.doubleString != null) {
            System.out.println("Match created");
            sceneUpdater.updateExistingMatches(createMatchResponse.doubleString);
        } else
            sceneUpdater.popUpInvalidMatchName();
    }

    /**
     * Handle method for login ReJoinResponse
     *
     * @param reJoinResponse contains the username
     */
    @Override
    public void handle(ReJoinResponse reJoinResponse) {
        System.out.println("Response Received, requesting rejoin in match");
        sceneUpdater.setUsernameInApplication(reJoinResponse.username);
        new ReJoinMatchRequest(reJoinResponse.matchName).handle(rmiHandler);
    }

    /**
     * Handle method for TimeOut Response
     *
     * @param timeOutResponse Creates a pop up indicating that the timer expired
     */
    @Override
    public void handle(TimeOutResponse timeOutResponse) {
        sceneUpdater.timeOut(timeOutResponse);
    }

    /**
     * Handle method for joinedMatchResponse
     *
     * @param joinedMatchResponse
     */
    @Override
    public void handle(JoinedMatchResponse joinedMatchResponse) {
        System.out.println("Received loginToMatch");
    }

    /**
     * Method that updates the view whenever a User placed a die in their window
     *
     * @param updateViewResponse response that's going to trigger the view update
     */
    @Override
    public void handle(UpdateViewResponse updateViewResponse) {
        sceneUpdater.updateView(updateViewResponse);
    }

    /**
     * Method that passes the four possible pattern cards to the View
     *
     * @param patternCardNotification
     */
    @Override
    public void handle(PatternCardNotification patternCardNotification) {
        sceneUpdater.launchThirdGui(patternCardNotification);
    }

    /**
     * Method that passes the initial objects needed for the game to the fourth view
     *
     * @param boardDataResponse class that holds every object needed for the game to start
     */
    @Override
    public void handle(BoardDataResponse boardDataResponse) {
        sceneUpdater.launchFourthGui(boardDataResponse);
    }

    /**
     * Method that handles the drafted dice by passing them to the View
     *
     * @param draftedDiceResponse response that holds the dice drafted from the board
     */
    @Override
    public void handle(DraftedDiceResponse draftedDiceResponse) {
        sceneUpdater.setDraftedDice(draftedDiceResponse.dice);
    }

    /**
     * Method that handle the responses from the server after the user send his move for a ToolCard
     * Depending on the ToolCard Type, the switch-case calls the relative interface method, throws overloading,
     * to the SceneUpdater
     *
     * @param useToolCardResponse response from the Server that contains information about the toolCard move
     */
    @Override
    public void handle(UseToolCardResponse useToolCardResponse) {
        switch (useToolCardResponse.toolCardType) {
            case PATTERN_CARD:
                sceneUpdater.toolCardAction((PatternCardToolCardResponse) useToolCardResponse);
                break;
            case DRAFT_POOL:
                sceneUpdater.toolCardAction((DraftedDiceToolCardResponse) useToolCardResponse);
                break;
            case ROUND_TRACK:
                sceneUpdater.toolCardAction((RoundTrackToolCardResponse) useToolCardResponse);
                break;
            case FLUX_BRUSH:
                sceneUpdater.toolCardAction((FluxBrushResponse) useToolCardResponse);
                break;
            case GROZING_PLIERS:
                sceneUpdater.toolCardAction((GrozingPliersResponse) useToolCardResponse);
                break;
            case FLUX_REMOVER:
                sceneUpdater.toolCardAction((FluxRemoverResponse) useToolCardResponse);
                break;
            case GRINDING_STONE:
                sceneUpdater.toolCardAction((GrindingStoneResponse) useToolCardResponse);
                break;
            case LATHEKIN:
                sceneUpdater.toolCardAction((LathekinResponse) useToolCardResponse);
                break;
            case COPPER_FOIL_BURNISHER:
                sceneUpdater.toolCardAction((CopperFoilBurnisherResponse) useToolCardResponse);
                break;
            case CORK_BACKED_STRAIGHT_EDGE:
                sceneUpdater.toolCardAction((CorkBackedStraightedgeResponse) useToolCardResponse);
                break;
            case LENS_CUTTER:
                sceneUpdater.toolCardAction((LensCutterResponse) useToolCardResponse);
                break;
            case TAP_WHEEL:
                sceneUpdater.toolCardAction((TapWheelResponse) useToolCardResponse);
                break;
            case EGLOMISE_BRUSH:
                sceneUpdater.toolCardAction((EglomiseBrushResponse) useToolCardResponse);
                break;
            case AVOID_USE:
                sceneUpdater.toolCardAction((AvoidToolCardResponse) useToolCardResponse);
                break;
            case RUNNING_PLIERS:
                sceneUpdater.toolCardAction((RunningPliersResponse) useToolCardResponse);
                break;
        }
    }

    /**
     * Method that tells the server that the user doesn't want to make a move
     * and passes the turn to the next player in line
     */
    @Override
    public void handle(EndTurnResponse endTurnResponse) {
        sceneUpdater.endedTurn();
    }

    /**
     * Handle of RoundTrackNotification
     *
     * @param roundTrackNotification Roundtrack updated
     */
    @Override
    public void handle(RoundTrackNotification roundTrackNotification) {
        sceneUpdater.updateRoundTrack(roundTrackNotification);
    }

    /**
     * Method that handles every Notification and acts differently for every Notification's enumeration element
     *
     * @param notification notification received by the ClientController
     */
    @Override
    public void handle(Notification notification) {
        switch (notification.notificationType) {
            case DRAFT_DICE:
                sceneUpdater.popUpDraftNotification();
                break;
            case START_TURN:
                sceneUpdater.startTurn((StartTurnNotification) notification);
                break;
            case HISTORY_UPDATE:
                sceneUpdater.updateMovesHistory((MoveStatusNotification) notification);
                break;
        }
    }

    /**
     * Method that handle the loseNotification
     *
     * @param loseNotification It contains the player's score
     */
    @Override
    public void handle(LoseNotification loseNotification) {
        sceneUpdater.showLostNotification(loseNotification.totalScore);
    }

    /**
     * Method that handle the winNotification
     *
     * @param victoryNotification It contains the player's score
     */
    @Override
    public void handle(VictoryNotification victoryNotification) {
        sceneUpdater.showWinnerNotification(victoryNotification.totalScore);
    }

    /**
     * Method that handle the rankingDataResponse
     *
     * @param rankingDataResponse Response that contains the ranking
     */
    @Override
    public void handle(RankingDataResponse rankingDataResponse) {
        sceneUpdater.updateRankingStatsTableView(rankingDataResponse.tripleString);
    }

    /**
     * Method that handle the bundleDataResponse
     *
     * @param bundleDataResponse Response returned by the server with the lobby data
     */
    @Override
    public void handle(BundleDataResponse bundleDataResponse) {
        sceneUpdater.loadLobbyData(bundleDataResponse);
    }

    /**
     * Method that handle the finishedMatchesResponse
     *
     * @param finishedMatchesResponse It contains all the finished matches
     */
    @Override
    public void handle(FinishedMatchesResponse finishedMatchesResponse) {
        sceneUpdater.showFinishedMatches(finishedMatchesResponse.finishedMatchesList);
    }

    /**
     * Method that handle the historyResponse
     *
     * @param historyResponse contains the required match review
     */
    @Override
    public void handle(HistoryResponse historyResponse) {
        sceneUpdater.showSelectedMatchHistory(historyResponse.historyJSON);
    }

    /**
     * Method that handle the availablePositionsResponse
     *
     * @param availablePositionsResponse Response containing the available position
     *                                   for that game Phase(place dice, use toolcard ecc.)
     */
    @Override
    public void handle(AvailablePositionsResponse availablePositionsResponse) {
        sceneUpdater.setAvailablePositions(availablePositionsResponse.availablePositions);
    }
}
