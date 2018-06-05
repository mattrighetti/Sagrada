package ingsw.controller;

import ingsw.controller.network.commands.*;
import ingsw.model.Dice;
import ingsw.model.cards.patterncard.PatternCard;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteController extends Remote {

    void assignPatternCard(String username, PatternCard patternCard) throws RemoteException;

    void draftDice(String username) throws RemoteException;

    void sendAck() throws RemoteException;

    /* THESE METHODS HAVE TO PASS THE USERNAME OF THE PLAYER WHO CALLED THEM */

    void endTurn() throws RemoteException;

    void placeDice(Dice dice, int rowIndex, int columnIndex) throws RemoteException;

    void useToolCard(String toolCardName) throws RemoteException;

    void toolCardMove(GrozingPliersRequest grozingPliersRequest) throws RemoteException;

    void toolCardMove(FluxBrushRequest moveToolCardRequest) throws RemoteException;

    void toolCardMove(FluxRemoverRequest moveToolCardRequest) throws RemoteException;

    void toolCardMove(GrindingStoneRequest moveToolCardRequest) throws RemoteException;

    void toolCardMove(CopperFoilBurnisherRequest moveToolCardRequest) throws RemoteException;
}
