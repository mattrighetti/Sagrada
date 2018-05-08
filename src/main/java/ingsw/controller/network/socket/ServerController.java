package ingsw.controller.network.socket;

import ingsw.controller.Controller;
import ingsw.controller.network.commands.*;
import ingsw.exceptions.InvalidUsernameException;
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
    public synchronized Response handle(LoginUserRequest loginUserRequest) {
        try {
            user = sagradaGame.loginUser(loginUserRequest.username, clientHandler);
        } catch (InvalidUsernameException e) {
            return new LoginUserResponse(null, -1);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        return new LoginUserResponse(user, sagradaGame.getConnectedUsers());
    }

    @Override
    public Response handle(ChosenPatternCardRequest chosenPatternCard) throws RemoteException {
        PatternCard patternCard = null;
        patternCard = controller.assignPatternCard(chosenPatternCard.patternCard, user.getUsername());

        if (patternCard != null) {
            return new ChosenPatternCardResponse(user.getUsername(), patternCard);
        } else
            return null; //TODO ritorna un comando negativo generale
    }
}
