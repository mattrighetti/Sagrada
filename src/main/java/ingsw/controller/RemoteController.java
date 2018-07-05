package ingsw.controller;

import ingsw.controller.network.commands.*;
import ingsw.model.Dice;
import ingsw.model.User;
import ingsw.model.cards.patterncard.PatternCard;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interface used by the RMI Clients to call methods on the Controller
 */
public interface RemoteController extends Remote {

    /**
     * Assigns the pattern card to the player
     * @param username player
     * @param patternCard pattern card to assign
     * @throws RemoteException
     */
    void assignPatternCard(String username, PatternCard patternCard) throws RemoteException;

    /**
     * Draft the dice
     * @param username currentPlayer
     * @throws RemoteException
     */
    void draftDice(String username) throws RemoteException;

    /**
     * Sends ack to the GameManager
     * @throws RemoteException
     */
    void sendAck() throws RemoteException;

    /* THESE METHODS HAVE TO PASS THE USERNAME OF THE PLAYER WHO CALLED THEM */

    /**
     * Deactivate the user
     * @param user player to deactivate
     * @throws RemoteException
     */
    void deactivateUser(User user) throws RemoteException;

    /**
     * Ends the turn
     * @param currentPlayer current player
     * @throws RemoteException
     */
    void endTurn(String currentPlayer) throws RemoteException;

    /**
     * Place a dice
     * @param dice Dice to place
     * @param rowIndex Row index
     * @param columnIndex Column index
     * @throws RemoteException
     */
    void placeDice(Dice dice, int rowIndex, int columnIndex) throws RemoteException;

    /**
     * Use a tool card
     * @param toolCardName Tool card to use
     * @throws RemoteException
     */
    void useToolCard(String toolCardName) throws RemoteException;

    /**
     * Grozing Pliers move
     * @param grozingPliersRequest Request for the move
     * @throws RemoteException
     */
    void toolCardMove(GrozingPliersRequest grozingPliersRequest) throws RemoteException;

    /**
     * Flux Brush move
     * @param moveToolCardRequest Request for the move
     * @throws RemoteException
     */
    void toolCardMove(FluxBrushRequest moveToolCardRequest) throws RemoteException;

    /**
     * Flux Remover move
     * @param moveToolCardRequest Request for the move
     * @throws RemoteException
     */
    void toolCardMove(FluxRemoverRequest moveToolCardRequest) throws RemoteException;

    /**
     * Grinding Stone move
     * @param moveToolCardRequest Request for the move
     * @throws RemoteException
     */
    void toolCardMove(GrindingStoneRequest moveToolCardRequest) throws RemoteException;

    /**
     * Copper Foil Burnisher move
     * @param moveToolCardRequest Request for the move
     * @throws RemoteException
     */
    void toolCardMove(CopperFoilBurnisherRequest moveToolCardRequest) throws RemoteException;

    /**
     * Cork Backed Straight Edge move
     * @param moveToolCardRequest Request for the move
     * @throws RemoteException
     */
    void toolCardMove(CorkBackedStraightedgeRequest moveToolCardRequest) throws RemoteException;

    /**
     * Lens Cutter move
     * @param moveToolCardRequest Request for the move
     * @throws RemoteException
     */
    void toolCardMove(LensCutterRequest moveToolCardRequest) throws RemoteException;

    /**
     * Eglomise Brush move
     * @param moveToolCardRequest Request for the move
     * @throws RemoteException
     */
    void toolCardMove(EglomiseBrushRequest moveToolCardRequest) throws RemoteException;

    /**
     * Lathekin move
     * @param lathekinRequest Request for the move
     * @throws RemoteException
     */
    void toolCardMove(LathekinRequest lathekinRequest) throws RemoteException;

    /**
     * Running Pliers move
     * @param moveToolCardRequest Request for the move
     * @throws RemoteException
     */
    void toolCardMove(RunningPliersRequest moveToolCardRequest) throws RemoteException;

    /**
     * Tap Wheel move
     * @param moveToolCardRequest Request for the move
     * @throws RemoteException
     */
    void toolCardMove(TapWheelRequest moveToolCardRequest) throws RemoteException;
}
