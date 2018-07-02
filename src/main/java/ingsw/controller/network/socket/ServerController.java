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

    ServerController(ClientHandler clientHandler) {
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
        } catch (InvalidUsernameException | RemoteException e) {
            return new LoginUserResponse(null);
        }

        return null;
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
            sagradaGame.loginUserToController(joinMatchRequest.matchName, user.getUsername());
            controller = sagradaGame.getMatchController(joinMatchRequest.matchName);
        } catch (RemoteException e) {
            return new JoinedMatchResponse(false);
        }

        return new JoinedMatchResponse(true);
    }

    @Override
    public Response handle(ReJoinMatchRequest reJoinMatchRequest) {
        try {
            sagradaGame.loginPrexistentPlayer(reJoinMatchRequest.matchName, user.getUsername());
            controller = sagradaGame.getMatchController(reJoinMatchRequest.matchName);
        } catch (RemoteException e) {
            return new LoginUserResponse(user);
        }

        return null;
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
    public Response handle(BundleDataRequest bundleDataRequest) {
        try {
            sagradaGame.sendBundleData(user.getUsername());
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Response handle(MoveToolCardRequest moveToolCardRequest) {
        try {
            switch (moveToolCardRequest.toolCardType) {
                case GROZING_PLIERS:
                    controller.toolCardMove(((GrozingPliersRequest) moveToolCardRequest));
                    break;
                case FLUX_BRUSH:
                    controller.toolCardMove((FluxBrushRequest) moveToolCardRequest);
                    break;
                case FLUX_REMOVER:
                    controller.toolCardMove((FluxRemoverRequest) moveToolCardRequest);
                    break;
                case GRINDING_STONE:
                    controller.toolCardMove((GrindingStoneRequest) moveToolCardRequest);
                    break;
                case COPPER_FOIL_BURNISHER:
                    controller.toolCardMove((CopperFoilBurnisherRequest) moveToolCardRequest);
                    break;
                case CORK_BACKED_STRAIGHT_EDGE:
                    controller.toolCardMove((CorkBackedStraightedgeRequest) moveToolCardRequest);
                    break;
                case LENS_CUTTER:
                    controller.toolCardMove((LensCutterRequest) moveToolCardRequest);
                    break;
                case EGLOMISE_BRUSH:
                    controller.toolCardMove((EglomiseBrushRequest) moveToolCardRequest);
                    break;
                case LATHEKIN:
                    controller.toolCardMove((LathekinRequest) moveToolCardRequest);
                    break;
                case RUNNING_PLIERS:
                    controller.toolCardMove((RunningPliersRequest) moveToolCardRequest);
                    break;
                case TAP_WHEEL:
                    controller.toolCardMove((TapWheelRequest) moveToolCardRequest);
                    break;
                default:
                    break;
            }
        } catch (RemoteException e) {
            e.printStackTrace();
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
            controller.endTurn(endTurnRequest.player);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Response handle(FinishedMatchesRequest finishedMatchesRequest) {

        try {
            sagradaGame.sendFinishedMatchesList(user.getUsername());
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Response handle(ReadHistoryRequest readHistoryRequest) {

        try {
            sagradaGame.sendSelectedMatchHistory(user.getUsername(), readHistoryRequest.matchName);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Method that removes a user from the Server in case it did not join a match, otherwise it just deactivates it from
     * Sagrada and the match's Controller
     */
    public void deactivateUser() {
        try {
            // Deactivate user in the match's controller
            if (controller != null) {
                controller.deactivateUser(user);
                sagradaGame.deactivateUser(user);
            } else {
                System.err.println("The user did not join a match yet, removing the user from the Server");
                // Deactivate user in SagradaGame
                sagradaGame.logoutUser(user.getUsername());
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
