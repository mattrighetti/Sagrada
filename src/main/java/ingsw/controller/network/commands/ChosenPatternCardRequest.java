package ingsw.controller.network.commands;

import ingsw.model.cards.patterncard.PatternCard;

public class ChosenPatternCardRequest implements Request {
    public PatternCard patternCard;

    public ChosenPatternCardRequest(PatternCard chosenPatternCard) {
        this.patternCard = chosenPatternCard;
    }

    @Override
    public Response handle(RequestHandler requestHandler) {
        return requestHandler.handle(this);
    }
}
