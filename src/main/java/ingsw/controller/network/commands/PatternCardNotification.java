package ingsw.controller.network.commands;

import ingsw.model.cards.patterncard.PatternCard;

import java.util.List;

public class PatternCardNotification implements Response {

    public List<PatternCard> patternCards;

    public PatternCardNotification(List<PatternCard> patternCards) {
        this.patternCards = patternCards;
    }

    @Override
    public void handle(ResponseHandler responseHandler) {
        responseHandler.handle(this);
    }
}
