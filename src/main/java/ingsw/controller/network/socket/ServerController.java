package ingsw.controller.network.socket;

import ingsw.controller.Controller;
import ingsw.controller.network.commands.LoginUserRequest;
import ingsw.controller.network.commands.LoginUserResponse;
import ingsw.controller.network.commands.RequestHandler;
import ingsw.controller.network.commands.Response;
import ingsw.model.SagradaGame;
import ingsw.model.User;

import java.rmi.RemoteException;

public class ServerController implements RequestHandler {
    private ClientHandler clientHandler;
    private final SagradaGame sagradaGame;
    private Controller controller;
    private User user;

    public ServerController(ClientHandler clientHandler) throws RemoteException {
        this.clientHandler = clientHandler;
        sagradaGame = SagradaGame.get();
        System.out.println("Got sagrada!");
    }

    @Override
    public Response handle(LoginUserRequest loginUserRequest) {
        try {
            user = sagradaGame.loginUser(loginUserRequest.username);
        } catch (Exception e) {
            System.err.println();
        }

        return new LoginUserResponse(user);
    }
}
