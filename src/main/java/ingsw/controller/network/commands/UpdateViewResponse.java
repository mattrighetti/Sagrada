package ingsw.controller.network.commands;

import ingsw.model.Player;
import ingsw.model.cards.patterncard.PatternCard;
import ingsw.utilities.GridCreator;

import java.util.List;
import java.util.Map;

public class UpdateViewResponse implements Response {
    public Player player;
    public Map<String,Boolean[][]> availablePositions;

    public UpdateViewResponse(Player player, Map<String,Boolean[][]> availablePositions) {

        this.player = player;
        this.availablePositions = availablePositions;
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
