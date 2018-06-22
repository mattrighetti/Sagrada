package ingsw.controller.network.commands;

import ingsw.model.Player;

public class EndTurnRequest implements Request {

    public String player;

    public EndTurnRequest(String player) {
        this.player = player;
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
