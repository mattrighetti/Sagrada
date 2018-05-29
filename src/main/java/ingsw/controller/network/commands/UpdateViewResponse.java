package ingsw.controller.network.commands;

import ingsw.model.Player;
import ingsw.model.cards.patterncard.PatternCard;
import ingsw.utilities.GridCreator;

public class UpdateViewResponse implements Response {
    public Player player;
    public String string;

    public UpdateViewResponse(Player player) {
        this.player = player;
        this.string = GridCreator.serializePatternCard(player);
    }

    public PatternCard deserializePatternCard() {
        return GridCreator.fromString(string, player.getPatternCard());
    }

    /**
     * Method that declares by which this response should be handled
     * @param responseHandler class that handles the Request
     */
    @Override
    public void handle(ResponseHandler responseHandler) {
        responseHandler.handle(this);
    }
}
