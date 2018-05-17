package ingsw.controller.network.commands;

import ingsw.model.cards.patterncard.PatternCard;

import java.rmi.RemoteException;

public class ChosenPatternCardRequest implements Request {
    public String username;
    public PatternCard patternCard;

    public ChosenPatternCardRequest(String username, PatternCard chosenPatternCard) {
        this.username = username;
        this.patternCard = chosenPatternCard;
    }

    @Override
    public Response handle(RequestHandler requestHandler) throws RemoteException {
        return requestHandler.handle(this);
    }
}
