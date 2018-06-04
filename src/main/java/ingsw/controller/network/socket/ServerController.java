package ingsw.controller.network.socket;

import ingsw.controller.Controller;
import ingsw.controller.network.commands.*;
import ingsw.exceptions.InvalidUsernameException;
import ingsw.model.SagradaGame;
import ingsw.model.User;

import java.io.Serializable;
import java.rmi.RemoteException;

public class ServerController implements RequestHandler, Serializable {
    private transient ClientHandler clientHandler;
    private final transient SagradaGame sagradaGame;
    private transient Controller controller;
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
    public Response handle(LogoutRequest logoutRequest) {
        try {
            sagradaGame.logoutUser(user.getUsername());
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        return new LogoutResponse();
    }

    @Override
    public Response handle(CreateMatchRequest createMatchRequest) {
        try {
            sagradaGame.createMatch(createMatchRequest.matchName);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Response handle(JoinMatchRequest joinMatchRequest) {
        try {
            sagradaGame.loginUserToController(joinMatchRequest.matchName, user);
            controller = sagradaGame.getMatchController(joinMatchRequest.matchName);
        } catch (RemoteException e) {
            e.printStackTrace();
            return new JoinedMatchResponse(false);
        }

        return new JoinedMatchResponse(true);
    }

    @Override
    public Response handle(ChosenPatternCardRequest chosenPatternCard) {
        try {
            controller.assignPatternCard(user.getUsername(), chosenPatternCard.patternCard);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Response handle(DraftDiceRequest draftDiceRequest) {
        try {
            controller.draftDice(user.getUsername());
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Response handle(MoveToolCardRequest moveToolCardRequest) {
        switch (moveToolCardRequest.toolCardType){
            case GROZING_PLIERS:
                try {
                    controller.toolCardMove(((GrozingPliersRequest) moveToolCardRequest));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case FLUX_BRUSH:
                try {
                    controller.toolCardMove((FluxBrushRequest) moveToolCardRequest);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
        }
        return null;
    }

    @Override
    public Response handle(Ack ack) {
        try {
            controller.sendAck();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Response handle(PlaceDiceRequest placeDiceRequest) {
        try {
            controller.placeDice(placeDiceRequest.dice, placeDiceRequest.rowIndex, placeDiceRequest.columnIndex);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Response handle(UseToolCardRequest useToolCardRequest) {
        try {
            controller.useToolCard(useToolCardRequest.toolCardName);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Response handle(EndTurnRequest endTurnRequest) {
        try {
            controller.endTurn();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        return null;
    }
}
