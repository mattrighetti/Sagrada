package ingsw.controller.network.socket;

import ingsw.controller.network.commands.*;
import ingsw.view.SceneUpdater;

/**
 * Class that defines the socket connection of the game
 */
public class ClientController implements ResponseHandler {
    private BroadcastReceiver broadcastReceiver;
    private Client client;
    private boolean isUserLogged = false;
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

    public boolean loginUser(String username) {
        client.request(new LoginUserRequest(username));
        client.nextResponse().handle(this);
        return isUserLogged;
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

    @Override
    public void handle(LoginUserResponse loginUserResponse) {
        if (loginUserResponse.user != null) {
            System.out.println("New connection >>> " + loginUserResponse.user);
            isUserLogged = true;
            //listenForNewUsers();
        } else {
            isUserLogged = false;
            System.out.println("False");
        }
    }

    @Override
    public void handle(IntegerResponse integerResponse) {
        System.out.println("Connected Users: " + integerResponse.number);
        sceneUpdater.updateConnectedUsers(integerResponse.number);
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
