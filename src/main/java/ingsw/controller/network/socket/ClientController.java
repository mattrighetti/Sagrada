package ingsw.controller.network.socket;

import ingsw.controller.network.commands.LoginUserRequest;
import ingsw.controller.network.commands.LoginUserResponse;
import ingsw.controller.network.commands.ResponseHandler;
import ingsw.view.View;

/**
 * Class that defines the socket connection of the game
 */
public class ClientController implements ResponseHandler {
    private Client client;
    private final View view;

    public ClientController(Client client, View view) {
        this.client = client;
        this.view = view;
    }


    public void loginUser(String username) {
        client.request(new LoginUserRequest(username));
        client.nextResponse().handle(this);
    }

    @Override
    public void handle(LoginUserResponse loginUserResponse) {
        System.out.println("User logged successfully!");
    }

}
