package ingsw.controller.network.commands;

import ingsw.model.cards.patterncard.PatternCard;

public class ChosenPatternCardRequest extends CommandRequest implements Request {
    public PatternCard patternCard;

    public ChosenPatternCardRequest(String username, PatternCard chosenPatternCard) {
        super(username);
        this.patternCard = chosenPatternCard;
    }

    @Override
    public Response handle(RequestHandler requestHandler) {
        return requestHandler.handle(this);
    }
}
