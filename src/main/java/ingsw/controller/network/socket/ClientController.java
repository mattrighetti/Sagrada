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

    @Override
    public boolean loginUser(String username) {
        client.request(new LoginUserRequest(username));
        client.nextResponse().handle(this);
        return isUserLogged;
    }

    @Override
    public void createMatch(String matchName) {
        client.request(new CreateMatchRequest(matchName));
    }

    /**
     * Method that opens a Thread and listens for every incoming JoinedUserResponse sent by the Controller
     *
     */
    public void listenForNewUsers() {
        broadcastReceiver.start();
        System.out.println("Thread started");
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
            isUserLogged = true;
            try {
                sceneUpdater.updateConnectedUsers(loginUserResponse.connectedUsers);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            listenForNewUsers();
        } else {
            isUserLogged = false;
        }
    }

    @Override
    public void handle(IntegerResponse integerResponse) {
        try {
            sceneUpdater.updateConnectedUsers(integerResponse.number);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handle(CreateMatchResponse createMatchResponse) {

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
