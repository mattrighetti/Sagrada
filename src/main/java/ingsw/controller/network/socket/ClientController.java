package ingsw.controller.network.socket;

import ingsw.controller.network.commands.*;
import ingsw.model.Dice;
import ingsw.model.cards.patterncard.PatternCard;
import ingsw.view.SceneUpdater;
import ingsw.controller.network.NetworkType;

/**
 * Class that defines the socket connection of the game
 */
public class ClientController implements ResponseHandler, NetworkType {
    private Client client;
    private Thread thread;
    private boolean generalPurposeBoolean = false;
    private boolean hasMatchBeenCreated = false;
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
        client.nextResponse().handle(this);
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
        //stopBroadcastReceiver();
        client.request(new JoinMatchRequest(matchName));
        //client.nextResponse().handle(this);
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
     * @param dice dice placed in the window
     * @param columnIndex column index where the die has been placed
     * @param rowIndex row index where the die has been placed
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
    public void endTurn() {
        client.request(new EndTurnRequest());
    }

    /**
     * Method that opens a Thread and listens for every incoming Response sent by the Controller
     */
    private void listenForResponses() {
        thread = new Thread(
                () -> {
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
                }
        );
        thread.start();
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
            sceneUpdater.updateConnectedUsers(loginUserResponse.connectedUsers);
            sceneUpdater.updateExistingMatches(loginUserResponse.availableMatches);
            sceneUpdater.launchSecondGui(loginUserResponse.user.getUsername());
            listenForResponses();
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
     * @param updateViewResponse
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
     * @param notification
     */
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

    @Override
    public void handle(UseToolCardResponse useToolCardResponse) {
        // TODO
    }
}
