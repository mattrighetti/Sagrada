package ingsw.controller.network.commands;

import ingsw.model.Player;
import ingsw.model.cards.patterncard.PatternCard;
import ingsw.utilities.GridCreator;

import java.util.List;

public class UpdateViewResponse implements Response {
    public Player player;
    public List<Boolean[][]> availablePositions;

    public UpdateViewResponse(Player player, List<Boolean[][]> availablePositions) {

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
