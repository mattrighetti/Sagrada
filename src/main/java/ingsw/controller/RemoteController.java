package ingsw.controller;

import ingsw.model.Dice;
import ingsw.model.cards.patterncard.PatternCard;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteController extends Remote {

    void assignPatternCard(String username, PatternCard patternCard) throws RemoteException;

    void draftDice(String username) throws RemoteException;

    void sendAck() throws RemoteException;

    void endTurn() throws RemoteException;

    void placeDice(Dice dice, int rowIndex, int columnIndex) throws RemoteException;

    void useToolCard(String toolCardName) throws RemoteException;
}
