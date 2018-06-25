package ingsw.controller.network.rmi;

import ingsw.controller.network.NetworkType;
import ingsw.controller.network.commands.*;
import ingsw.model.Dice;
import ingsw.model.cards.patterncard.PatternCard;
import ingsw.utilities.Tuple;
import ingsw.view.SceneUpdater;

import java.rmi.RemoteException;

public class RMIController implements ResponseHandler, NetworkType {
    private RMIHandler rmiHandler;
    private RMIUserObserver rmiUserObserver;
    private Response response;
    private SceneUpdater sceneUpdater;

    public void connect(String ipAddress) {
        try {
            rmiUserObserver = new RMIUserObserver(this);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        rmiHandler = new RMIHandler(this, rmiUserObserver, ipAddress);
    }

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

    @Override
    public void logoutUser() {
        response = new LogoutRequest().handle(rmiHandler);
        response.handle(this);
    }

    @Override
    public void createMatch(String matchName) {
        new CreateMatchRequest(matchName).handle(rmiHandler);
    }

    @Override
    public void joinExistingMatch(String matchName) {
        response = new JoinMatchRequest(matchName).handle(rmiHandler);
        response.handle(this);
    }

    @Override
    public void requestBundleData() {
        new BundleDataRequest().handle(rmiHandler);
    }

    @Override
    public void choosePatternCard(PatternCard patternCard) {
        new ChosenPatternCardRequest(patternCard).handle(rmiHandler);
    }

    @Override
    public void draftDice() {
        new DraftDiceRequest().handle(rmiHandler);
    }

    @Override
    public void sendAck() {
        new Ack().handle(rmiHandler);
    }

    @Override
    public void placeDice(Dice dice, int columnIndex, int rowIndex) {
        new PlaceDiceRequest(dice, columnIndex, rowIndex).handle(rmiHandler);
    }

    @Override
    public void useToolCard(String toolCardName) {
        new UseToolCardRequest(toolCardName).handle(rmiHandler);
    }

    @Override
    public void grozingPliersMove(Dice dice, boolean increase) {
        new GrozingPliersRequest(dice, increase).handle(rmiHandler);
    }

    @Override
    public void fluxBrushMove(Dice dice) {
        new FluxBrushRequest(dice).handle(rmiHandler);
    }

    @Override
    public void fluxBrushMove(Dice selectedDice, int rowIndex, int columnIndex) {
        new FluxBrushRequest(selectedDice, rowIndex, columnIndex).handle(rmiHandler);
    }

    @Override
    public void fluxBrushMove() {
        new FluxBrushRequest().handle(rmiHandler);
    }

    @Override
    public void fluxRemoverMove(Dice dice) {
        new FluxRemoverRequest(dice).handle(rmiHandler);
    }

    @Override
    public void fluxRemoverMove(Dice dice, int chosenValue) {
        new FluxRemoverRequest(dice, chosenValue).handle(rmiHandler);
    }

    @Override
    public void fluxRemoverMove() {
        new FluxBrushRequest().handle(rmiHandler);
    }

    @Override
    public void fluxRemoverMove(Dice selectedDice, int columnIndex, int rowIndex) {
        new FluxBrushRequest(selectedDice, rowIndex, columnIndex).handle(rmiHandler);
    }

    @Override
    public void copperFoilBurnisherMove(Tuple dicePosition, Tuple position) {
        new CopperFoilBurnisherRequest(dicePosition, position).handle(rmiHandler);
    }

    @Override
    public void corkBackedStraightedgeMove(Dice selectedDice, int row, int column) {
        new CorkBackedStraightedgeRequest(selectedDice, row, column).handle(rmiHandler);
    }

    @Override
    public void grindingStoneMove(Dice dice) {
        new GrindingStoneRequest(dice).handle(rmiHandler);
    }

    @Override
    public void lensCutter(int roundIndex, String roundTrackDice, String poolDice) {
        new LensCutterRequest(roundIndex, roundTrackDice, poolDice).handle(rmiHandler);
    }

    @Override
    public void runningPliersMove(Dice selectedDice, int rowIndex, int columnIndex) {
        new RunningPliersRequest(selectedDice, rowIndex, columnIndex).handle(rmiHandler);
    }

    @Override
    public void lathekinMove(Tuple dicePosition, Tuple position, boolean doubleMove) {
        new LathekinRequest(dicePosition, position, doubleMove).handle(rmiHandler);
    }

    @Override
    public void tapWheelMove(Dice dice, int phase) {
        new TapWheelRequest(dice, phase).handle(rmiHandler);
    }

    @Override
    public void tapWheelMove(int endTapWheel) {
        new TapWheelRequest(endTapWheel).handle(rmiHandler);
    }

    @Override
    public void tapWheelMove(Tuple dicePosition, Tuple position, int phase, boolean doubleMove) {
        new TapWheelRequest(dicePosition, position, phase, doubleMove).handle(rmiHandler);
    }

    @Override
    public void requestHistory(String matchName) {
        new ReadHistoryRequest(matchName).handle(rmiHandler);
    }

    @Override
    public void requestFinishedMatches() {
        new FinishedMatchesRequest().handle(rmiHandler);
    }

    @Override
    public void endTurn(String player) {
        new EndTurnRequest(player).handle(rmiHandler);
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /* HANDLER PART */
    /* METHOD EXECUTED BY THE RMI HANDLER */


    @Override
    public void handle(LoginUserResponse loginUserResponse) {
        if (loginUserResponse.user != null) {
            loginUserResponse.user.addListener(rmiUserObserver);
            System.out.println("New connection >>> " + loginUserResponse.user.getUsername());

            sceneUpdater.launchSecondGui(loginUserResponse.user.getUsername());
        } else
            sceneUpdater.launchAlert();
    }

    @Override
    public void handle(LogoutResponse logoutResponse) {
        if (logoutResponse != null) {
            sceneUpdater.closeStage();
        }
    }

    @Override
    public void handle(IntegerResponse integerResponse) {
        System.out.println("Connected Users: " + integerResponse.number);

        sceneUpdater.updateConnectedUsers(integerResponse.number);
    }

    @Override
    public void handle(CreateMatchResponse createMatchResponse) {
        if (createMatchResponse.doubleString != null) {
            System.out.println("Match created");

            sceneUpdater.updateExistingMatches(createMatchResponse.doubleString);
        }
    }

    @Override
    public void handle(ReJoinResponse reJoinResponse) {
        System.out.println("Response Received, requesting rejoin in match");
        sceneUpdater.setUsernameInApplication(reJoinResponse.username);
        sceneUpdater.launchProgressForm();
        new ReJoinMatchRequest(reJoinResponse.matchName).handle(rmiHandler);
    }

    @Override
    public void handle(TimeOutResponse timeOutResponse) {
        sceneUpdater.timeOut();
    }

    @Override
    public void handle(JoinedMatchResponse joinedMatchResponse) {
        System.out.println("Received logintomatch");
    }

    @Override
    public void handle(UpdateViewResponse updateViewResponse) {
        sceneUpdater.updateView(updateViewResponse);
    }

    @Override
    public void handle(PatternCardNotification patternCardNotification) {
        sceneUpdater.launchThirdGui(patternCardNotification);
    }

    @Override
    public void handle(BoardDataResponse boardDataResponse) {
        sceneUpdater.launchFourthGui(boardDataResponse);
    }

    @Override
    public void handle(DraftedDiceResponse draftedDiceResponse) {
        sceneUpdater.setDraftedDice(draftedDiceResponse.dice);
    }

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
            case AVOID_USE:
                sceneUpdater.toolCardAction((AvoidToolCardResponse) useToolCardResponse);
                break;
            case RUNNING_PLIERS:
                sceneUpdater.toolCardAction((RunningPliersResponse) useToolCardResponse);
                break;
        }
    }

    @Override
    public void handle(EndTurnResponse endTurnResponse) {
        sceneUpdater.endedTurn();
    }

    @Override
    public void handle(RoundTrackNotification roundTrackNotification) {
        sceneUpdater.updateRoundTrack(roundTrackNotification);
    }

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

    @Override
    public void handle(LoseNotification loseNotification) {
        sceneUpdater.showLostNotification(loseNotification.totalScore);
    }

    @Override
    public void handle(VictoryNotification victoryNotification) {
        sceneUpdater.showWinnerNotification(victoryNotification.totalScore);
    }

    @Override
    public void handle(RankingDataResponse rankingDataResponse) {
        sceneUpdater.updateRankingStatsTableView(rankingDataResponse.tripleString);
    }

    @Override
    public void handle(BundleDataResponse bundleDataResponse) {
        sceneUpdater.loadLobbyData(bundleDataResponse);
    }

    @Override
    public void handle(FinishedMatchesResponse finishedMatchesResponse) {
        sceneUpdater.showFinishedMatches(finishedMatchesResponse.finishedMatchesList);
    }

    @Override
    public void handle(HistoryResponse historyResponse) {
        sceneUpdater.showSelectedMatchHistory(historyResponse.historyJSON);
    }

    @Override
    public void handle(AvailablePositionsResponse availablePositionsResponse) {
        sceneUpdater.setAvailablePositions(availablePositionsResponse.availablePositions);
    }
}
