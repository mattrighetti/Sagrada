package ingsw.controller.network.socket;

import ingsw.controller.Controller;
import ingsw.controller.network.commands.*;
import ingsw.exceptions.InvalidUsernameException;
import ingsw.model.SagradaGame;
import ingsw.model.User;
import ingsw.model.cards.toolcards.GrindingStone;

import java.io.Serializable;
import java.rmi.RemoteException;

public class ServerController implements RequestHandler, Serializable {
    private transient ClientHandler clientHandler;
    private final transient SagradaGame sagradaGame;
    private transient Controller controller;
    private User user;

    ServerController(ClientHandler clientHandler) throws RemoteException {
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
            e.printStackTrace();
            return new JoinedMatchResponse(false);
        }

        return new JoinedMatchResponse(true);
    }

    @Override
    public Response handle(ReJoinMatchRequest reJoinMatchRequest) {
        try {
            sagradaGame.loginPrexistentPlayer(reJoinMatchRequest.matchName, user);
            controller = sagradaGame.getMatchController(reJoinMatchRequest.matchName);
        } catch (RemoteException e) {
            e.printStackTrace();
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
                break;
            case FLUX_REMOVER:
                try {
                    controller.toolCardMove((FluxRemoverRequest) moveToolCardRequest);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case GRINDING_STONE:
                try {
                    controller.toolCardMove((GrindingStoneRequest) moveToolCardRequest);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case COPPER_FOIL_BURNISHER:
                try {
                    controller.toolCardMove((CopperFoilBurnisherRequest) moveToolCardRequest);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case CORK_BACKED_STRAIGHT_EDGE:
                try {
                    controller.toolCardMove((CorkBackedStraightedgeRequest) moveToolCardRequest);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case LENS_CUTTER:
                try {
                    controller.toolCardMove((LensCutterRequest) moveToolCardRequest);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case LATHEKIN:
                try {
                    controller.toolCardMove((LathekinRequest) moveToolCardRequest);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case TAP_WHEEL:
                try {
                    controller.toolCardMove((TapWheelRequest) moveToolCardRequest);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
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

    @Override
    public Response handle(ReadHistoryRequest readHistoryRequest) {
        return null; // TODO
    }

    /**
     * Method that removes a user from the Server in case it did not join a match, otherwise it just deactivates it from
     * Sagrada and the match's Controller
     */
    void deactivateUser() {
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
