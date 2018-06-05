package ingsw.controller.network.rmi;

import ingsw.controller.network.NetworkType;
import ingsw.controller.network.commands.*;
import ingsw.model.Dice;
import ingsw.model.cards.patterncard.PatternCard;
import ingsw.model.cards.toolcards.EglomiseBrush;
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
     * @return boolean value that indicates if the user has been successfully logged in to the game
     * @throws RemoteException throws RemoteException
     */
    @Override
    public void loginUser(String username) {
        response = new LoginUserRequest(username).handle(rmiHandler);
        response.handle(this);
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
    public void fluxRemoverMove(Dice dice) {
        new FluxRemoverRequest(dice).handle(rmiHandler);
    }

    @Override
    public void copperFoilBurnisherMove(Tuple dicePosition, Tuple position) {
        new CopperFoilBurnisherRequest(dicePosition, position).handle(rmiHandler);
    }

    @Override
    public void corkBackedStraightedgeMove(Dice selectedDice, int row, int column) {
        new CorkBackedStraightedgeRequest(selectedDice,row,column).handle(rmiHandler);
    }

    @Override
    public void grindingStoneMove(Dice dice) {
        new GrindingStoneRequest(dice).handle(rmiHandler);
    }

    @Override
    public void endTurn() {
        new EndTurnRequest().handle(rmiHandler);
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /* HANDLER PART */
    /* METHOD EXECUTED BY THE RMI HANDLER */


    @Override
    public void handle(LoginUserResponse loginUserResponse) {
        if (loginUserResponse.user != null) {
            loginUserResponse.user.addListener(rmiUserObserver);
            System.out.println("New connection >>> " + loginUserResponse.user.getUsername());

            sceneUpdater.updateConnectedUsers(loginUserResponse.connectedUsers);
            sceneUpdater.updateExistingMatches(loginUserResponse.availableMatches);
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
    public void handle(MessageResponse messageResponse) {

    }

    @Override
    public void handle(CreateMatchResponse createMatchResponse) {
        if (createMatchResponse.doubleString != null) {
            System.out.println("Match created");

            sceneUpdater.updateExistingMatches(createMatchResponse.doubleString);
        }
    }

    @Override
    public void handle(JoinedMatchResponse joinedMatchResponse) {

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
        switch (useToolCardResponse.toolCardType){
            case FLUX_REMOVER:
                sceneUpdater.toolCardAction((FluxRemoverResponse) useToolCardResponse);
                break;
            case GROZING_PLIERS:
                sceneUpdater.toolCardAction((GrozingPliersResponse) useToolCardResponse);
                break;
            case FLUX_BRUSH:
                sceneUpdater.toolCardAction((FluxBrushResponse) useToolCardResponse);
                break;
            case GRINDING_STONE:
                sceneUpdater.toolCardAction((GrindingStoneResponse) useToolCardResponse);
                break;
            case DRAFT_POOL:
                sceneUpdater.toolCardAction((DraftPoolResponse) useToolCardResponse);
                break;
            case COPPER_FOIL_BURNISHER:
                sceneUpdater.toolCardAction((CopperFoilBurnisherResponse) useToolCardResponse);
                break;
            case CORK_BACKED_STRAIGHT_EDGE:
                sceneUpdater.toolCardAction((CorkBackedStraightedgeResponse) useToolCardResponse);
                break;
            case EGLOMISE_BRUSH:
                sceneUpdater.toolCardAction((EglomiseBrushResponse) useToolCardResponse);
                break;
        }
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
                sceneUpdater.setAvailablePosition((StartTurnNotification) notification);
                break;
        }
    }
}
