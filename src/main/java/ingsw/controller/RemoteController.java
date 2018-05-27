package ingsw.controller;

import ingsw.model.cards.patterncard.PatternCard;

import java.rmi.Remote;
import java.rmi.RemoteException;

/* INTERFACCIA DELLE CHIAMATE AL CONTROLLER CHE VERRANNO EFFETTUATE DA SAGRADAGAME */
public interface RemoteController extends Remote {

    void assignPatternCard(String username, PatternCard patternCard) throws RemoteException;

    void draftDice(String username) throws RemoteException;

    void sendAck() throws RemoteException;
}
