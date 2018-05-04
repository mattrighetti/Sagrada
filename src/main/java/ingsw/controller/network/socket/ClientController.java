package ingsw.controller.network.socket;

import ingsw.view.View;

/**
 * Class that defines the socket connection of the game
 */
public class ClientController {
    private Client client;
    private Thread receiver;

    private final View view;

    public ClientController(Client client, View view) {
        this.client = client;
        this.view = view;
    }
}
