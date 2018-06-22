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

    public ClientController(Client client) {
        this.client = client;
    }

    public void setSceneUpdater(SceneUpdater sceneUpdater) {
        this.sceneUpdater = sceneUpdater;
    }

    Client getClient() {
        return client;
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /* EXCLUSIVE SOCKET PART */

    /**
     * Method that stops the active receiver thread triggering a null response
     */
    private void stopBroadcastReceiver() {
        client.stopBroadcastReceiver();
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
        client.request(new LoginUserRequest(username));
        if (!listenerActive) {
            listenForResponses();
        }
    }

    /**
     * Method that logs out the user from SagradaGame and the Match
     */
    @Override
    public void logoutUser() {
        stopBroadcastReceiver();
        client.request(new LogoutRequest());
        client.nextResponse().handle(this);
    }

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

    @Override
    public void grindingStoneMove(Dice dice) {
        client.request(new GrindingStoneRequest(dice));
    }

    @Override
    public void lensCutter(int roundIndex, String roundTrackDice, String poolDice) {
        client.request(new LensCutterRequest(roundIndex, roundTrackDice, poolDice));
    }

    @Override
    public void runningPliersMove(Dice selectedDice, int rowIndex, int columnIndex) {
        client.request(new RunningPliersRequest(selectedDice, rowIndex, columnIndex));
    }

    @Override
    public void tapWheelMove(Dice dice, int phase) {
        client.request(new TapWheelRequest(dice, phase));
    }

    @Override
    public void tapWheelMove(Tuple dicePosition, Tuple position, int phase, boolean doubleMove) {
        client.request(new TapWheelRequest(dicePosition, position, phase, doubleMove));
    }

    @Override
    public void tapWheelMove(int endTapWheel) {
        client.request(new TapWheelRequest(endTapWheel));
    }

    @Override
    public void grozingPliersMove(Dice dice, boolean increase) {
        client.request(new GrozingPliersRequest(dice, increase));
    }

    @Override
    public void fluxBrushMove(Dice dice) {
        client.request(new FluxBrushRequest(dice));
    }

    @Override
    public void fluxBrushMove(Dice selectedDice, int rowIndex, int columnIndex) {
        client.request(new FluxBrushRequest(selectedDice, rowIndex, columnIndex));
    }

    @Override
    public void fluxBrushMove() {
        client.request((new FluxBrushRequest()));
    }

    @Override
    public void fluxRemoverMove(Dice dice) {
        client.request(new FluxRemoverRequest(dice));
    }

    @Override
    public void fluxRemoverMove(Dice dice, int chosenValue) {
        client.request(new FluxRemoverRequest(dice, chosenValue));
    }

    @Override
    public void fluxRemoverMove() {
        client.request(new FluxRemoverRequest());
    }

    @Override
    public void fluxRemoverMove(Dice selectedDice, int columnIndex, int rowIndex) {
        client.request(new FluxRemoverRequest(selectedDice, rowIndex, columnIndex));
    }

    @Override
    public void copperFoilBurnisherMove(Tuple dicePosition, Tuple position) {
        client.request(new CopperFoilBurnisherRequest(dicePosition, position));
    }

    @Override
    public void corkBackedStraightedgeMove(Dice selectedDice, int row, int column) {
        client.request((new CorkBackedStraightedgeRequest(selectedDice, row, column)));
    }

    @Override
    public void lathekinMove(Tuple dicePosition, Tuple position, boolean doubleMove) {
        client.request(new LathekinRequest(dicePosition, position, doubleMove));
    }

    @Override
    public void requestFinishedMatches() {
        client.request(new FinishedMatchesRequest());
    }

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
                            response.handle(this);
                            System.out.println("Received a response: " + response);
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
        client.close();
        sceneUpdater.closeStage();
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
     * Method that prints a message received by either the Controller or SagradaGame
     *
     * @param messageResponse response sent by the Server-side running classes
     *                        whenever a user sends a message to broadcast
     */
    @Override
    public void handle(MessageResponse messageResponse) {
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

    @Override
    public void handle(ReJoinResponse reJoinResponse) {
        System.out.println("Response Received, requesting rejoin in match");
        sceneUpdater.setUsernameInApplication(reJoinResponse.username);
        sceneUpdater.launchProgressForm();
        client.request(new ReJoinMatchRequest(reJoinResponse.matchName));
    }

    @Override
    public void handle(BundleDataResponse bundleDataResponse) {
        sceneUpdater.loadLobbyData(bundleDataResponse);
    }

    @Override
    public void handle(LoseNotification loseNotification) {
        sceneUpdater.showLostNotification(loseNotification.totalScore);
    }

    @Override
    public void handle(VictoryNotification victoryNotification) {
        sceneUpdater.showWinnerNotification(victoryNotification.totalScore);
    }

    @Override
    public void handle(TimeOutResponse timeOutResponse) {
        sceneUpdater.timeOut();
    }

    @Override
    public void handle(FinishedMatchesResponse finishedMatchesResponse) {
        sceneUpdater.showFinishedMatches(finishedMatchesResponse.finishedMatchesList);
    }

    @Override
    public void handle(EndTurnResponse endTurnResponse) {
        sceneUpdater.endedTurn();
    }

    @Override
    public void handle(AvailablePositionsResponse availablePositionsResponse) {
        sceneUpdater.setAvailablePositions(availablePositionsResponse.availablePositions);
    }

    @Override
    public void handle(HistoryResponse historyResponse) {
        sceneUpdater.showSelectedMatchHistory(historyResponse.historyJSON);
    }

    @Override
    public void handle(RankingDataResponse rankingDataResponse) {
        sceneUpdater.updateRankingStatsTableView(rankingDataResponse.tripleString);
    }
}
