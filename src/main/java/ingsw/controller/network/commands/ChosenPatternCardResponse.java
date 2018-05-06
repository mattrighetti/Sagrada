package ingsw.controller.network.commands;

import ingsw.model.cards.patterncard.PatternCard;

public class ChosenPatternCardResponse extends CommandResponse implements Response {
    public PatternCard chosenPatternCard;

    public ChosenPatternCardResponse(String username, PatternCard patternCard) {
        super(username);
        this.chosenPatternCard = patternCard;
    }

    @Override
    public void handle(ResponseHandler responseHandler) {
        responseHandler.handle(this);
    }
}
