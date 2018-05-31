package ingsw.controller.network.rmi;

import ingsw.controller.RemoteController;
import ingsw.controller.network.commands.*;
import ingsw.exceptions.InvalidUsernameException;
import ingsw.model.RemoteSagradaGame;
import ingsw.model.User;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class RMIHandler implements RequestHandler {
    private ResponseHandler rmiController;
    private RMIUserObserver rmiUserObserver;
    private RemoteSagradaGame sagradaGame;
    private RemoteController remoteController;
    private User user;

    /**
     * RMIHandler constructor which retrieves SagradaGame and sets
     *
     * @param rmiController
     * @param rmiUserObserver
     */
    RMIHandler(RMIController rmiController, RMIUserObserver rmiUserObserver) {
        try {
            //this.sagradaGame = (RemoteSagradaGame) LocateRegistry.getRegistry().lookup("sagrada");
            try {
                this.sagradaGame = (RemoteSagradaGame) Naming.lookup("rmi://localhost:1099/sagrada");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

        } catch (RemoteException e) {
            System.err.println("Could not retrieve SagradaGame");
            e.printStackTrace();
        } catch (NotBoundException e) {
            System.err.println("NotBoundException: SagradaGame");
            e.printStackTrace();
        }
        this.rmiController = rmiController;
        this.rmiUserObserver = rmiUserObserver;
    }

    @Override
    public Response handle(LoginUserRequest loginUserRequest) {
        try {
            user = sagradaGame.loginUser(loginUserRequest.username, rmiUserObserver);
            return new LoginUserResponse(user, sagradaGame.getConnectedUsers(), sagradaGame.doubleStringBuilder());
        } catch (InvalidUsernameException | RemoteException e) {
            return new LoginUserResponse(null, -1, null);
        }
    }

    @Override
    public Response handle(LogoutRequest logoutRequest) {
        try {
            sagradaGame.logoutUser(user.getUsername());
            return new LogoutResponse();
        } catch (RemoteException e) {
            return null;
        }
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
            try {
                remoteController = (RemoteController) Naming.lookup("rmi://localhost:1099/" + joinMatchRequest.matchName);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            return new JoinedMatchResponse(true);
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
            return new JoinedMatchResponse(false);
        }
    }

    @Override
    public Response handle(ChosenPatternCardRequest chosenPatternCardRequest) {
        try {
            remoteController.assignPatternCard(user.getUsername(), chosenPatternCardRequest.patternCard);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Response handle(DraftDiceRequest draftDiceRequest) {
        try {
            remoteController.draftDice(user.getUsername());
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Response handle(Ack ack) {
        try {
            remoteController.sendAck();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Response handle(PlaceDiceRequest placeDiceRequest) {
        try {
            remoteController.placeDice(placeDiceRequest.dice, placeDiceRequest.rowIndex, placeDiceRequest.columnIndex);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Response handle(UseToolCardRequest useToolCardRequest) {
        try {
            remoteController.useToolCard(useToolCardRequest.toolCardName);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Response handle(EndTurnRequest endTurnRequest) {
        try {
            remoteController.endTurn();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        return null;
    }
}