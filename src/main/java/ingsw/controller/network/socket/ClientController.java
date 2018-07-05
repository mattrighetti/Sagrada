package ingsw.controller.network.socket;

import ingsw.controller.network.commands.*;
import ingsw.model.Dice;
import ingsw.model.cards.patterncard.PatternCard;
import ingsw.utilities.Tuple;
import ingsw.view.SceneUpdater;
import ingsw.controller.network.NetworkType;

/**
 * Class that defines the socket connection of the game
 */
public class ClientController implements ResponseHandler, NetworkType {
    private Client client;
    private boolean listenerActive = false;
    private SceneUpdater sceneUpdater;

    /**
     * Assign a client
     * @param client
     */
    public ClientController(Client client) {
        this.client = client;
    }

    /**
     * Set the Scene Updater
     * @param sceneUpdater FXML Controller
     */
    public void setSceneUpdater(SceneUpdater sceneUpdater) {
        this.sceneUpdater = sceneUpdater;
    }

    /* NETWORK TYPE PART */
    /* METHODS EXECUTED BY THE VIEW TO MAKE ACTIONS */


    /**
     * Method that logs in the user to SagradaGame
     *
     * @param username username chosen by the user
     */
    @Override
    public void loginUser(String username) {
        if (!listenerActive) {
            listenForResponses();
        }

        client.request(new LoginUserRequest(username));
    }

    /**
     * Request the bundle data
     */
    @Override
    public void requestBundleData() {
        client.request(new BundleDataRequest());
    }

    /**
     * Method that creates a match
     *
     * @param matchName name of the match to create
     */
    @Override
    public void createMatch(String matchName) {
        client.request(new CreateMatchRequest(matchName));
    }

    /**
     * Method that logs the user into the match
     *
     * @param matchName name of the match to join
     */
    @Override
    public void joinExistingMatch(String matchName) {
        client.request(new JoinMatchRequest(matchName));
    }

    /**
     * Method that sets the chosen pattern card to the game model
     *
     * @param patternCard pattern card chosen by the player
     */
    @Override
    public void choosePatternCard(PatternCard patternCard) {
        client.request(new ChosenPatternCardRequest(patternCard));
    }

    /**
     * Method that drafts dice from the board
     */
    @Override
    public void draftDice() {
        client.request(new DraftDiceRequest());
    }

    /**
     * Method that sends an acknowledgement to the server-side whenever it's needed
     */
    @Override
    public void sendAck() {
        client.request(new Ack());
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
        client.request(new PlaceDiceRequest(dice, columnIndex, rowIndex));
    }

    /**
     * Method that tells to the Server that the player wants to use a toolcard
     *
     * @param toolCardName
     */
    @Override
    public void useToolCard(String toolCardName) {
        client.request(new UseToolCardRequest(toolCardName));
    }

    /**
     * Method that tells the server that the user doesn't want to make a move
     * and passes the turn to the next player in line
     */
    @Override
    public void endTurn(String player) {
        client.request(new EndTurnRequest(player));
    }

    /**
     * Method that creates the request for Grinding Stone
     *
     * @param dice Selected dice
     */
    @Override
    public void grindingStoneMove(Dice dice) {
        client.request(new GrindingStoneRequest(dice));
    }

    /**
     * Method that creates the request for Lens Cutter
     *
     * @param roundIndex Round Index
     * @param roundTrackDice Dice to extract from the roundTrack
     * @param poolDice Dice To insert again in the pool dice
     */
    @Override
    public void lensCutter(int roundIndex, String roundTrackDice, String poolDice) {
        client.request(new LensCutterRequest(roundIndex, roundTrackDice, poolDice));
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
        client.request(new RunningPliersRequest(selectedDice, rowIndex, columnIndex));
    }

    /**
     * Method that creates the request for TapWheel
     *
     * @param dice Dice from the round track
     * @param phase Phase of Tap Wheel
     */
    @Override
    public void tapWheelMove(Dice dice, int phase) {
        client.request(new TapWheelRequest(dice, phase));
    }

    /**
     * Method that creates the request for TapWheel
     *
     * @param dicePosition Dice initial position
     * @param position Dice final position or second dice position(in case of double move)
     * @param phase Phase of Tap Wheel
     * @param doubleMove DoubleMove
     */
    @Override
    public void tapWheelMove(Tuple dicePosition, Tuple position, int phase, boolean doubleMove) {
        client.request(new TapWheelRequest(dicePosition, position, phase, doubleMove));
    }

    /**
     * Method that creates the request for TapWheel
     *
     * @param endTapWheel End phase Tap Wheel
     */
    @Override
    public void tapWheelMove(int endTapWheel) {
        client.request(new TapWheelRequest(endTapWheel));
    }

    /**
     * Method that creates the request for Gronzing Pliers
     *
     * @param dice Selected dice
     * @param increase Increase/decrease value
     */
    @Override
    public void grozingPliersMove(Dice dice, boolean increase) {
        client.request(new GrozingPliersRequest(dice, increase));
    }

    /**
     * Method that creates the request for Flux Brush
     *
     * @param dice Selected dice
     */
    @Override
    public void fluxBrushMove(Dice dice) {
        client.request(new FluxBrushRequest(dice));
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
        client.request(new FluxBrushRequest(selectedDice, rowIndex, columnIndex));
    }

    /**
     * Method that creates the request for Flux Brush
     */
    @Override
    public void fluxBrushMove() {
        client.request((new FluxBrushRequest()));
    }

    /**
     * Method that creates the request for Flux Remover
     *
     * @param dice Selected dice
     */
    @Override
    public void fluxRemoverMove(Dice dice) {
        client.request(new FluxRemoverRequest(dice));
    }

    /**
     * Method that creates the request for Flux Remover
     *
     * @param dice Selected dice
     * @param chosenValue new face up value
     */
    @Override
    public void fluxRemoverMove(Dice dice, int chosenValue) {
        client.request(new FluxRemoverRequest(dice, chosenValue));
    }

    /**
     * Method that creates the request for Flux Remover
     */
    @Override
    public void fluxRemoverMove() {
        client.request(new FluxRemoverRequest());
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
        client.request(new FluxRemoverRequest(selectedDice, rowIndex, columnIndex));
    }

    /**
     * Method that creates the request for Copper Foil Burnisher
     *
     * @param dicePosition Initial dice position
     * @param position Final dice position
     */
    @Override
    public void copperFoilBurnisherMove(Tuple dicePosition, Tuple position) {
        client.request(new CopperFoilBurnisherRequest(dicePosition, position));
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
        client.request((new CorkBackedStraightedgeRequest(selectedDice, row, column)));
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
        client.request(new LathekinRequest(dicePosition, position, doubleMove));
    }

    /**
     * Method that creates the request for the finished matches
     */
    @Override
    public void requestFinishedMatches() {
        client.request(new FinishedMatchesRequest());
    }

    /**
     * Method that creates the request for the history
     *
     * @param matchName Name of the match
     */
    @Override
    public void requestHistory(String matchName) {
        client.request(new ReadHistoryRequest(matchName));
    }

    /**
     * Method that opens a Thread and listens for every incoming Response sent by the Controller
     */
    private void listenForResponses() {
        new Thread(
                () -> {
                    listenerActive = true;
                    System.out.println("Opening the Thread");
                    Response response;
                    do {
                        response = client.nextResponse();
                        if (response != null) {
                            if (!(response instanceof Ping)) {
                                response.handle(this);
                                System.out.println("Received a response: " + response);
                            } else client.ackPing(new Ping());
                        } else {
                            System.err.println("Null received, stopping broadcast");
                            break;
                        }
                    } while (true);
                    listenerActive = false;
                }
        ).start();
    }


    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /* HANDLER PART */
    /* METHOD EXECUTED BY THE CLIENT HANDLER */

    /**
     * Method that is executed every time a User logs into the game.
     * It updates the number of users connected and the available matches in the View.
     *
     * @param loginUserResponse response sent by SagradaGame every time a user logs into the game
     */
    @Override
    public void handle(LoginUserResponse loginUserResponse) {
        if (loginUserResponse.user != null) {
            sceneUpdater.launchSecondGui(loginUserResponse.user.getUsername());
        } else
            sceneUpdater.launchAlert();
    }

    /**
     * Method that handles the (sudden) logout of a user
     *
     * @param logoutResponse response that triggers the user logout
     */
    @Override
    public void handle(LogoutResponse logoutResponse) {
        if (logoutResponse.logoutSuccessful) {
            System.out.println("BYE BYE!");
            sceneUpdater.closeStage();
        } else
            sceneUpdater.launchAlert();
    }

    /**
     * Method that updates the number of connected users in the View.
     *
     * @param integerResponse response sent by SagradaGame every time a user logs into the game
     */
    @Override
    public void handle(IntegerResponse integerResponse) {
        sceneUpdater.updateConnectedUsers(integerResponse.number);
    }

    /**
     * Method that updates the list of matches in the application's TableView
     *
     * @param createMatchResponse response that encapsulates the new list of available matches
     */
    @Override
    public void handle(CreateMatchResponse createMatchResponse) {
        if (createMatchResponse.doubleString != null) {
            System.out.println("Match created");

            sceneUpdater.updateExistingMatches(createMatchResponse.doubleString);
        }
    }

    /**
     * Method used to confirm to every user their successful login
     *
     * @param joinedMatchResponse response that encapsulates the successful login boolean
     */
    @Override
    public void handle(JoinedMatchResponse joinedMatchResponse) {
        System.out.println("Received logintomatch");
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
            case HISTORY_UPDATE:
                sceneUpdater.updateMovesHistory((MoveStatusNotification) notification);
                break;
            case START_TURN:
                sceneUpdater.startTurn((StartTurnNotification) notification);
                break;
        }
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
     * Handle of RoundTrackNotification
     *
     * @param roundTrackNotification Roundtrack updated
     */
    @Override
    public void handle(RoundTrackNotification roundTrackNotification) {
        sceneUpdater.updateRoundTrack(roundTrackNotification);
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
            case EGLOMISE_BRUSH:
                sceneUpdater.toolCardAction((EglomiseBrushResponse) useToolCardResponse);
                break;
            case RUNNING_PLIERS:
                sceneUpdater.toolCardAction((RunningPliersResponse) useToolCardResponse);
                break;
            case AVOID_USE:
                sceneUpdater.toolCardAction((AvoidToolCardResponse) useToolCardResponse);
                break;
            case TAP_WHEEL:
                sceneUpdater.toolCardAction((TapWheelResponse) useToolCardResponse);
                break;
        }
    }

    /**
     * Method that handle the ReJoinResponse
     *
     * @param reJoinResponse Response returned by the server with the username of the player
     *                       and the Controller name to join
     */
    @Override
    public void handle(ReJoinResponse reJoinResponse) {
        System.out.println("Response Received, requesting rejoin in match");
        sceneUpdater.setUsernameInApplication(reJoinResponse.username);
        client.request(new ReJoinMatchRequest(reJoinResponse.matchName));
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
     * Method that handle the TimeOutResponse
     *
     * @param timeOutResponse It creates a pop-up in which is written that
     *                        the timer is ended and the player automatically
     *                        the turn.
     */
    @Override
    public void handle(TimeOutResponse timeOutResponse) {
        sceneUpdater.timeOut(timeOutResponse);
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
     * Method that handle the endTurnResponse
     *
     * @param endTurnResponse Response that disables all the view commands
     */
    @Override
    public void handle(EndTurnResponse endTurnResponse) {
        sceneUpdater.endedTurn();
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
     * Method that handle the rankingDataResponse
     *
     * @param rankingDataResponse Response that contains the ranking
     */
    @Override
    public void handle(RankingDataResponse rankingDataResponse) {
        sceneUpdater.updateRankingStatsTableView(rankingDataResponse.tripleString);
    }
}
