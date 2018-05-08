package ingsw.controller.network.socket;

import ingsw.controller.Controller;
import ingsw.controller.network.commands.*;
import ingsw.model.SagradaGame;
import ingsw.model.User;
import ingsw.model.cards.patterncard.PatternCard;

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

        user.addListener(clientHandler);
        return new LoginUserResponse(user);
    }

    @Override
    public Response handle(ChosenPatternCardRequest chosenPatternCard) {
        PatternCard patternCard = controller.assignPatternCard(user.getUsername(), chosenPatternCard.patternCard);
        if (patternCard != null) {
            return new ChosenPatternCardResponse(user.getUsername(), patternCard);
        } else
            return null; //TODO ritorna un comando negativo generale
    }

}
