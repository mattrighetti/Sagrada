package ingsw.controller.network.socket;

import ingsw.controller.network.commands.*;
import ingsw.view.SceneUpdater;
import ingsw.controller.network.NetworkType;

/**
 * Class that defines the socket connection of the game
 */
public class ClientController implements ResponseHandler, NetworkType {
    private BroadcastReceiver broadcastReceiver;
    private Client client;
    private boolean generalPurposeBoolean = false;
    private boolean hasMatchBeenCreated = false;
    private SceneUpdater sceneUpdater;

    public ClientController(Client client) {
        this.client = client;
        this.broadcastReceiver = new BroadcastReceiver(this);
    }

    public void setSceneUpdater(SceneUpdater sceneUpdater) {
        this.sceneUpdater = sceneUpdater;
    }

    public Client getClient() {
        return client;
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

    @Override
    public void createMatch(String matchName) {
        client.request(new CreateMatchRequest(matchName));
    }

    /**
     * Method that opens a Thread and listens for every incoming JoinedUserResponse sent by the Controller
     */
    public void listenForNewUsers() {
        broadcastReceiver.start();
    }

    /**
     * Method that stops the BroadcastReceiver
     */
    public void stopReceiver() {
        broadcastReceiver.stop();
    }

    /**
     * Method that opens a Thread and listens for every incoming Response sent by the Controller
     */
    public void listenForResponses() {
        broadcastReceiver.restart();
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
            listenForNewUsers();
        } else
            generalPurposeBoolean = false;
    }

    @Override
    public void handle(IntegerResponse integerResponse) {
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
    public void handle(ChosenPatternCardResponse chosenPatternCardResponse) {
        //TODO
    }

    @Override
    public void handle(MessageResponse messageResponse) {
        System.out.println(messageResponse.message);
    }

}
