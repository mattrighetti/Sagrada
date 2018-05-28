package ingsw.controller.network.commands;

import ingsw.model.cards.patterncard.PatternCard;

import java.util.List;

public class PatternCardNotification implements Response {
    public List<PatternCard> patternCards;

    public PatternCardNotification(List<PatternCard> patternCards) {
        this.patternCards = patternCards;
    }

    /**
     * Method that returns a Response after the Request has been properly handled
     * @param responseHandler class that handles the Request
     */
    @Override
    public void handle(ResponseHandler responseHandler) {
        responseHandler.handle(this);
    }
}
