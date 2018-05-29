package ingsw.controller.network.commands;

import ingsw.model.Player;

public class UpdateViewResponse implements Response {
    public Player player;
    public String string;

    public UpdateViewResponse(Player player, String string) {
        this.player = player;
        this.string = string;
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
