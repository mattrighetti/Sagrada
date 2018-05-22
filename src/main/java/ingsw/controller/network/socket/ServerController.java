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
    }

    /**
     * Method that handles a LoginUserRequest
     *
     * @param loginUserRequest request
     * @return a Response, if the user has been logged in successfully, that is going to be handled
     * by the ClientController, otherwise it returns a negative Response
     */
    @Override
    public Response handle(LoginUserRequest loginUserRequest) {
        try {
            user = sagradaGame.loginUser(loginUserRequest.username, clientHandler);
            return new LoginUserResponse(user, sagradaGame.getConnectedUsers(), sagradaGame.doubleStringBuilder());
        } catch (InvalidUsernameException | RemoteException e) {
            return new LoginUserResponse(null, -1, null);
        }
    }

    @Override
    public void handle(CreateMatchRequest createMatchRequest) {
        try {
            sagradaGame.createMatch(createMatchRequest.matchName);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Response handle(JoinMatchRequest joinMatchRequest) {
        try {
            sagradaGame.loginUserToController(joinMatchRequest.matchName, user);
            controller = sagradaGame.getMatchController(joinMatchRequest.matchName);
            return new JoinedMatchResponse(true);
        } catch (RemoteException e) {
            e.printStackTrace();
            return new JoinedMatchResponse(false);
        }
    }

    @Override
    public Response handle(ChosenPatternCardRequest chosenPatternCard) {
        PatternCard patternCard = controller.assignPatternCard(user.getUsername(), chosenPatternCard.patternCard);
        if (patternCard != null) {
            return new ChosenPatternCardResponse(user.getUsername(), patternCard);
        } else return null;

    }

}
