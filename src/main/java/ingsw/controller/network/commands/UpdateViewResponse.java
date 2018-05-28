package ingsw.controller.network.commands;

import ingsw.model.Dice;
import ingsw.model.Player;

public class UpdateViewResponse implements Response {
    public Player player;

    public UpdateViewResponse(Player player) {
        this.player = player;
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
