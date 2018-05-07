package ingsw.controller.network.commands;

import ingsw.model.cards.patterncard.PatternCard;

public class ChosenPatternCardResponse implements Response {
    public String username;
    public PatternCard chosenPatternCard;

    public ChosenPatternCardResponse(String username, PatternCard patternCard) {
        this.username = username;
        this.chosenPatternCard = patternCard;
    }

    @Override
    public void handle(ResponseHandler responseHandler) {
        responseHandler.handle(this);
    }
}
