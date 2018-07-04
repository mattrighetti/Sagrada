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

public class RMIHandler implements RequestHandler {
    private String ipAddress;
    private ResponseHandler rmiController;
    private RMIUserObserver rmiUserObserver;
    private RemoteSagradaGame sagradaGame;
    private RemoteController remoteController;
    private User user;

    private static final String RMI_SLASH = "rmi://";
    private static final String RMI_PORT = ":1099/";

    /**
     * RMIHandler constructor which retrieves SagradaGame and sets
     *
     * @param rmiController   match's controller
     * @param rmiUserObserver user's UserObserver
     */
    RMIHandler(RMIController rmiController, RMIUserObserver rmiUserObserver, String ipAddress) {
        this.ipAddress = ipAddress;
        try {
            this.sagradaGame = (RemoteSagradaGame) Naming.lookup(rebindSagradaUrl(ipAddress));
        } catch (RemoteException | MalformedURLException e) {
            System.err.println("Could not retrieve SagradaGame");
            e.printStackTrace();
        } catch (NotBoundException e) {
            System.err.println("NotBoundException: SagradaGame");
            e.printStackTrace();
        }
        this.rmiController = rmiController;
        this.rmiUserObserver = rmiUserObserver;
    }

    private String rebindSagradaUrl(String ipAddress) {
        return RMI_SLASH + ipAddress + RMI_PORT + "sagrada";
    }

    private String rebindControllerUrl(String ipAddress, JoinMatchRequest joinMatchRequest) {
        return RMI_SLASH + ipAddress + RMI_PORT + joinMatchRequest.matchName;
    }

    private String rebindControllerUrl(String ipAddress, ReJoinMatchRequest reJoinMatchRequest) {
        return RMI_SLASH + ipAddress + RMI_PORT + reJoinMatchRequest.matchName;
    }

    /**
     * Method that request a User login to Sagrada
     *
     * @param loginUserRequest login request
     * @return null
     */
    @Override
    public Response handle(LoginUserRequest loginUserRequest) {
        try {
            user = sagradaGame.loginUser(loginUserRequest.username, rmiUserObserver);
        } catch (InvalidUsernameException | RemoteException e) {
            new LoginUserResponse(null).handle(rmiController);
        }

        return null;
    }

    /**
     * Method that sends a LogoutRequest to Sagrada
     *
     * @param logoutRequest logout request
     * @return null if the disconnection wasn't successful, a new LogoutResponse in case the user has been correctly
     * deactivated.
     */
    @Override
    public Response handle(LogoutRequest logoutRequest) {
        try {
            sagradaGame.logoutUser(user.getUsername());
            new LogoutResponse(true);
        } catch (RemoteException e) {
            System.err.println(e.getMessage());
            new LogoutResponse(false).handle(rmiController);
        }

        return null;
    }

    /**
     * Method that requests a User disconnection to Sagrada
     *
     * @param disconnectionRequest disconnection request
     * @return null
     */
    @Override
    public Response handle(DisconnectionRequest disconnectionRequest) {
        try {
            sagradaGame.deactivateUser(user.getUsername());
            new LogoutResponse(true).handle(rmiController);
        } catch (RemoteException e) {
            System.err.println(e.getMessage());
            new LogoutResponse(false).handle(rmiController);
        }

        return null;
    }

    /**
     * Method that requests a match creation to Sagrada
     *
     * @param createMatchRequest create match request
     * @return null
     */
    @Override
    public Response handle(CreateMatchRequest createMatchRequest) {
        try {
            sagradaGame.createMatch(createMatchRequest.matchName);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Method that requests a BundleData to Sagrada
     *
     * @param bundleDataRequest bundle data request
     * @return null
     */
    @Override
    public Response handle(BundleDataRequest bundleDataRequest) {
        try {
            sagradaGame.sendBundleData(user.getUsername());
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Method that filtrates the kind of requests by the ToolCard used in the move
     *
     * @param moveToolCardRequest ToolCard move request
     * @return null
     */
    @Override
    public Response handle(MoveToolCardRequest moveToolCardRequest) {
        try {
            switch (moveToolCardRequest.toolCardType) {
                case GROZING_PLIERS:
                    remoteController.toolCardMove(((GrozingPliersRequest) moveToolCardRequest));
                    break;
                case FLUX_REMOVER:
                    remoteController.toolCardMove((FluxRemoverRequest) moveToolCardRequest);
                    break;
                case FLUX_BRUSH:
                    remoteController.toolCardMove((FluxBrushRequest) moveToolCardRequest);
                    break;
                case GRINDING_STONE:
                    remoteController.toolCardMove((GrindingStoneRequest) moveToolCardRequest);
                    break;
                case COPPER_FOIL_BURNISHER:
                    remoteController.toolCardMove((CopperFoilBurnisherRequest) moveToolCardRequest);
                    break;
                case CORK_BACKED_STRAIGHT_EDGE:
                    remoteController.toolCardMove((CorkBackedStraightedgeRequest) moveToolCardRequest);
                    break;
                case LENS_CUTTER:
                    remoteController.toolCardMove((LensCutterRequest) moveToolCardRequest);
                    break;
                case EGLOMISE_BRUSH:
                    remoteController.toolCardMove((EglomiseBrushRequest) moveToolCardRequest);
                    break;
                case LATHEKIN:
                    remoteController.toolCardMove((LathekinRequest) moveToolCardRequest);
                    break;
                case RUNNING_PLIERS:
                    remoteController.toolCardMove((RunningPliersRequest) moveToolCardRequest);
                    break;
                case TAP_WHEEL:
                    remoteController.toolCardMove((TapWheelRequest) moveToolCardRequest);
                    break;
                default:
                    break;
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Method that requests a User join to match to Sagrada
     *
     * @param joinMatchRequest join match request
     * @return a false JoinedMatchResponse that alerts an unsuccessful join to match or a successful JoinedMatchResponse
     * in case the login to match was successful
     */
    @Override
    public Response handle(JoinMatchRequest joinMatchRequest) {
        try {
            sagradaGame.loginUserToController(joinMatchRequest.matchName, user.getUsername());
            remoteController = (RemoteController) Naming.lookup(rebindControllerUrl(ipAddress, joinMatchRequest));
            return new JoinedMatchResponse(true);
        } catch (RemoteException | NotBoundException | MalformedURLException e) {
            e.printStackTrace();
            return new JoinedMatchResponse(false);
        }
    }

    /**
     * Method that requests a re-join request to match to Sagrada, used when a user has previously disconnected from the
     * match
     *
     * @param reJoinMatchRequest rejoin match request with the match's name and the username
     * @return null
     */
    @Override
    public Response handle(ReJoinMatchRequest reJoinMatchRequest) {
        try {
            sagradaGame.loginPrexistentPlayer(reJoinMatchRequest.matchName, user.getUsername());
            remoteController = (RemoteController) Naming.lookup(rebindControllerUrl(ipAddress, reJoinMatchRequest));
        } catch (NotBoundException | RemoteException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            System.err.println("Error in URL String");
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Method that signals the controller which PatternCard has been chosen by the user
     *
     * @param chosenPatternCardRequest request that contains the user's chosen PatternCard
     * @return null
     */
    @Override
    public Response handle(ChosenPatternCardRequest chosenPatternCardRequest) {
        try {
            remoteController.assignPatternCard(user.getUsername(), chosenPatternCardRequest.patternCard);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Method that signals a draft action to the match's controller
     *
     * @param draftDiceRequest request
     * @return null
     */
    @Override
    public Response handle(DraftDiceRequest draftDiceRequest) {
        try {
            remoteController.draftDice(user.getUsername());
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Method that sends a positive Ack to the controller
     *
     * @param ack positive Ack
     * @return null
     */
    @Override
    public Response handle(Ack ack) {
        try {
            remoteController.sendAck();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Method that signals the controller a dice placing action
     *
     * @param placeDiceRequest request containing the necessary data to make the move
     * @return null
     */
    @Override
    public Response handle(PlaceDiceRequest placeDiceRequest) {
        try {
            remoteController.placeDice(placeDiceRequest.dice, placeDiceRequest.rowIndex, placeDiceRequest.columnIndex);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Method that signals the controller the will to use a ToolCard
     *
     * @param useToolCardRequest request containing the necessary data to make the move
     * @return null
     */
    @Override
    public Response handle(UseToolCardRequest useToolCardRequest) {
        try {
            remoteController.useToolCard(useToolCardRequest.toolCardName);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Method that requests the list of the finished matches
     *
     * @param finishedMatchesRequest request
     * @return null
     */
    @Override
    public Response handle(FinishedMatchesRequest finishedMatchesRequest) {

        try {
            sagradaGame.sendFinishedMatchesList(user.getUsername());
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Method that requests a history of a selected FinishedMatch
     *
     * @param readHistoryRequest request containing the necessary data to elaborate the request
     * @return null
     */
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
     * Method that signals the controller that the user wants to finish his turn
     *
     * @param endTurnRequest request
     * @return null
     */
    @Override
    public Response handle(EndTurnRequest endTurnRequest) {
        try {
            remoteController.endTurn(endTurnRequest.player);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        return null;
    }
}