package ingsw.controller.network.socket;

import ingsw.controller.network.commands.LoginUserRequest;
import ingsw.controller.network.commands.LoginUserResponse;
import ingsw.controller.network.commands.Response;
import ingsw.controller.network.commands.ResponseHandler;
import ingsw.view.View;

/**
 * Class that defines the socket connection of the game
 */
public class ClientController implements ResponseHandler {
    private Client client;
    private final View view;
    private Thread receiver;

    public ClientController(Client client, View view) {
        this.client = client;
        this.view = view;
    }

    public void loginUser(String username) {
        client.request(new LoginUserRequest(username));
        client.nextResponse().handle(this);
    }

    public void listenForNewUsers() {
        receiver = new Thread(
                () -> {
                    Response response;
                    do {
                        response = client.nextResponse();
                        if (response != null) {
                            response.handle(this);
                        }
                    } while (response != null);
                }
        );
        receiver.start();
    }

    @Override
    public void handle(LoginUserResponse loginUserResponse) {
        System.out.println(loginUserResponse.user.getUsername());
    }

}
