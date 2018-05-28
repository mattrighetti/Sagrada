package ingsw.controller.network.commands;

import ingsw.model.cards.patterncard.PatternCard;

public class ChosenPatternCardRequest implements Request {
    public PatternCard patternCard;

    public ChosenPatternCardRequest(PatternCard chosenPatternCard) {
        this.patternCard = chosenPatternCard;
    }

    /**
     * Method that returns a Response after the Request has been properly handled
     * @param requestHandler class that handles the Request
     * @return corresponding Response
     */
    @Override
    public Response handle(RequestHandler requestHandler) {
        return requestHandler.handle(this);
    }
}
