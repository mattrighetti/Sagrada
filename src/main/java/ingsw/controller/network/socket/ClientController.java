package ingsw.controller.network.socket;

import ingsw.controller.network.Message;
import ingsw.controller.network.commands.*;
import ingsw.view.View;

/**
 * Class that defines the socket connection of the game
 */
public class ClientController implements ResponseHandler {
    private BroadcastReceiver broadcastReceiver;
    private Client client;
    private final View view;

    public ClientController(Client client, View view) {
        this.client = client;
        this.view = view;
        this.broadcastReceiver = new BroadcastReceiver(this);
    }

    public Client getClient() {
        return client;
    }

    public void loginUser(String username) {
        client.request(new LoginUserRequest(username));
        client.nextResponse().handle(this);
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
        System.out.println(loginUserResponse.user.getUsername());
    }

    @Override
    public void handle(IntegerResponse integerResponse) {
        System.out.println("Connected Users: " + integerResponse.number);
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
