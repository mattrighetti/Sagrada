package ingsw.controller.network.socket;

import ingsw.controller.network.commands.*;
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

    public Client getClient() {
        return client;
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /* EXCLUSIVE SOCKET PART */

    /**
     * Method that stops the active receiver thread triggering a null response
     */
    public void stopBroadcastReceiver() {
        client.stopBroadcastReceiver();
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /* NETWORK TYPE PART */
    /* METHODS EXECUTED BY THE VIEW TO MAKE ACTIONS */


    /**
     * Method that logs in the user to SagradaGame
     *
     * @param username username chosen by the user
     * @return boolean value that indicates if the user has been successfully logged in to the game
     */
    @Override
    public boolean loginUser(String username) {
        client.request(new LoginUserRequest(username));
        client.nextResponse().handle(this);
        return generalPurposeBoolean;
    }

    /**
     * Method that creates a match
     * @param matchName name of the match to create
     */
    @Override
    public void createMatch(String matchName) {
        client.request(new CreateMatchRequest(matchName));
    }

    /**
     * Method that logs the user into the match
     * @param matchName name of the match to join
     * @return true if the login was successful or false if not
     */
    @Override
    public boolean joinExistingMatch(String matchName) {
        generalPurposeBoolean = false;
        stopBroadcastReceiver();
        client.request(new JoinMatchRequest(matchName));
        client.nextResponse().handle(this);

        return generalPurposeBoolean;
    }

    /**
     * Method that opens a Thread and listens for every incoming Response sent by the Controller
     */
    public void listenForResponses() {
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
                    System.out.println("Closing thread");
                }
        );
        thread.start();
    }


    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /* HANDLER PART */
    /* METHOD EXECUTED BY THE RMI HANDLER */


    @Override
    public void handle(LoginUserResponse loginUserResponse) {
        if (loginUserResponse.user != null) {
            generalPurposeBoolean = true;
            sceneUpdater.updateConnectedUsers(loginUserResponse.connectedUsers);
            sceneUpdater.updateExistingMatches(loginUserResponse.availableMatches);
            listenForResponses();
        } else
            generalPurposeBoolean = false;
    }

    @Override
    public void handle(IntegerResponse integerResponse) {
        sceneUpdater.updateConnectedUsers(integerResponse.number);
    }

    /**
     * Method that updates the list of matches in the application's TableView
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
     * @param joinedMatchResponse response that encapsulates the successful login boolean
     */
    @Override
    public void handle(JoinedMatchResponse joinedMatchResponse) {
        generalPurposeBoolean = joinedMatchResponse.isLoginSuccessful;
    }

    @Override
    public void handle(DiceMoveResponse diceMoveResponse) {
        //TODO
    }

    @Override
    public void handle(ChosenPatternCardResponse chosenPatternCardResponse) {
        //TODO
    }

    @Override
    public void handle(MessageResponse messageResponse) {
        System.out.println(messageResponse.message);
    }

}
